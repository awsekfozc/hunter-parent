package com.csair.csairmind.hunter.spider;

import com.csair.csairmind.hunter.common.constant.DataConstants;
import com.csair.csairmind.hunter.common.enums.SpriderEnums;
import com.csair.csairmind.hunter.common.util.SimHash;
import com.csair.csairmind.hunter.spider.distinct.ContentDistinct;
import com.csair.csairmind.hunter.spider.distinct.Distinct;
import com.csair.csairmind.hunter.spider.distinct.UrlDistinct;
import com.csair.csairmind.hunter.spider.exception.NoGetReadyException;
import com.csair.csairmind.hunter.spider.factory.RedisFactory;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsListProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsSingleProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.ResourcesProcessor;
import com.csair.csairmind.hunter.spider.schedule.DistinctScheduler;
import com.csair.csairmind.hunter.spider.schedule.ResourceTaskScheduler;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;
import us.codecraft.webmagic.thread.CountableThreadPool;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangcheng on 2017/5/23 0023.
 */
@Slf4j
public class ExpandSpider implements Task, Runnable {

    private Downloader downloader;
    private List<Pipeline> pipelines = new ArrayList();
    private PageProcessor pageProcessor;
    private List<Request> startRequests;
    private Site site;
    private String uuid;
    private DistinctScheduler scheduler;
    private int threadNum = 1;
    private AtomicInteger stat = new AtomicInteger(0);
    private boolean spawnUrl = true;
    private Request startRequest;
    private Distinct distinct;
    //任务类型，解析任务或者详情任务

    public static ExpandSpider create(PageProcessor pageProcessor, JedisPool pool) {
        return new ExpandSpider(pageProcessor, pool);
    }

    public ExpandSpider(PageProcessor pageProcessor, JedisPool pool) {
        RedisFactory.init(pool);
        this.pageProcessor = pageProcessor;
        this.site = pageProcessor.getSite();
        this.startRequests = pageProcessor.getSite().getStartRequests();
    }

    @Override
    public void run() {
        try {
            this.checkRunningStat();//检查组件是否齐全
            this.initComponent();//检查默认组件
            log.info("Spider " + this.getUUID() + " started!");
            if (startRequest != null) {
                if (pageProcessor instanceof ResourcesProcessor)//解析任务处理
                    this.processResourceRequest(startRequest);
                else if (pageProcessor instanceof DetailsSingleProcessor)//详情单条任务处理
                    this.processDetailsSingleRequest(startRequest);
                else if (pageProcessor instanceof DetailsListProcessor)//详情List任务处理
                    this.processDetailsListRequest(startRequest);
            }
        } catch (Exception var5) {
            log.error(var5.getMessage());
        } finally {
            this.close();
        }
    }


    /***
     * 资源解析任务处理
     * @param request
     */
    protected void processResourceRequest(Request request) {
        Page page = this.downloader.download(request, this);
        if (page == null) {
        } else if (page.isNeedCycleRetry()) {
            this.extractAndAddRequests(page, true);
        } else {
            this.pageProcessor.process(page);
            this.extractAndAddRequests(page, this.spawnUrl);
        }
    }

    /***
     * 解析单条详情任务处理
     * @param request
     */
    protected void processDetailsSingleRequest(Request request) {
        Page page = this.downloader.download(request, this);
        if (page == null) {
        } else {
            this.pageProcessor.process(page);
            if (!page.getResultItems().isSkip()) {
                Iterator var3 = this.pipelines.iterator();
                while (var3.hasNext()) {
                    Pipeline pipeline = (Pipeline) var3.next();
                    pipeline.process(page.getResultItems(), this);
                }
            }
            this.scheduler.pushDistinctQueue(request.getUrl());//任务完成，保存去重信息
        }
    }

    /***
     * 解析列表详情任务处理
     * @param request
     */
    protected void processDetailsListRequest(Request request) {
        Page page = this.downloader.download(request, this);
        if (page == null) {
        } else {
            this.pageProcessor.process(page);
            if (!page.getResultItems().isSkip()) {
                List<Map<String, Object>> data_list = page.getResultItems().get(DataConstants.DATA_LIEST);
                Iterator var3 = this.pipelines.iterator();
                String distinctCode = "";//将要保存的去重码，URL或内容特征码
                for (Map<String, Object> map : data_list) {
                    if (distinct instanceof UrlDistinct && distinct != null) {//如果是URL去重
                        distinctCode = map.get(DataConstants.DATA_URL).toString();
                        if (distinct.isDistinct(distinctCode))//如果存在URL，不添加到资源池中
                            continue;
                    } else if (distinct instanceof ContentDistinct && distinct != null) {//如果是内容去重
                        //内容去重的条件为标题+时间
                        String content = String.format("%s,%s", map.get(DataConstants.TITLE), map.get(DataConstants.DATE_TIME));
                        distinctCode = SimHash.getSimCode(content);
                        if (distinct.isDistinct(content))//如果存在URL，不添加到资源池中
                            continue;
                    }
                    while (var3.hasNext()) {
                        Pipeline pipeline = (Pipeline) var3.next();
                        pipeline.process(page.getResultItems(), this);
                    }
                    this.scheduler.pushDistinctQueue(distinctCode);//任务完成，保存去重信息
                }
            }

        }
    }

    /***
     * 检查爬虫相关组件
     */
    protected void initComponent() {
        if (this.downloader == null) {
            this.downloader = new HttpClientDownloader();
        }
        if (this.pipelines.isEmpty()) {
            this.pipelines.add(new ConsolePipeline());
        }
        this.downloader.setThread(this.threadNum);
    }

    /***
     * 上传新发现资源
     * @param page
     * @param spawnUrl
     */
    protected void extractAndAddRequests(Page page, boolean spawnUrl) {
        int i = 0;
        if (spawnUrl && CollectionUtils.isNotEmpty(page.getTargetRequests())) {
            Iterator var3 = page.getTargetRequests().iterator();
            while (var3.hasNext()) {
                Request request = (Request) var3.next();
                if (distinct instanceof UrlDistinct && distinct != null) {
                    if (distinct.isDistinct(request.getUrl()))//如果存在URL，不添加到资源池中
                        continue;
                }
                this.scheduler.push(request, this);
                i++;
            }
        }
        log.info("新增资源 {} 个", i);
    }


    /***
     * 检查爬虫是否准备就绪
     */
    private void checkRunningStat() throws NoGetReadyException {
        if (startRequest == null)
            throw new NoGetReadyException("初始请求未设置");
//        else if (scheduler == null)
//            throw new NoGetReadyException("资源调度器未设置");
//        else if (distinct == null)
//            throw new NoGetReadyException("去重规则未设置");
    }

    public ExpandSpider setUUID(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String getUUID() {
        if (this.uuid != null) {
            return this.uuid;
        } else if (this.site != null) {
            return this.site.getDomain();
        } else {
            this.uuid = UUID.randomUUID().toString();
            return this.uuid;
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    protected void sleep(int time) {
        try {
            Thread.sleep((long) time);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

    }

    /***
     * 释放资源
     */
    public void close() {
        this.destroyEach(this.downloader);
        this.destroyEach(this.pageProcessor);
        this.destroyEach(this.scheduler);
        Iterator var1 = this.pipelines.iterator();
        while (var1.hasNext()) {
            Pipeline pipeline = (Pipeline) var1.next();
            this.destroyEach(pipeline);
        }
    }

    private void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

    }

    public Downloader getDownloader() {
        return downloader;
    }

    public ExpandSpider setDownloader(Downloader downloader) {
        this.downloader = downloader;
        return this;
    }

    public ExpandSpider setSite(Site site) {
        this.site = site;
        return this;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public ExpandSpider setScheduler(DistinctScheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public Request getStartRequest() {
        return startRequest;
    }

    public ExpandSpider setStartRequest(String requestString) {
        this.startRequest = new Request(requestString);
        return this;
    }

    public ExpandSpider setStartRequest(Request startRequest) {
        this.startRequest = startRequest;
        return this;
    }

    public ExpandSpider setDistinct(Distinct distinct) {
        this.distinct = distinct;
        return this;
    }

    public ExpandSpider setPipeline(Pipeline pipeline) {
        pipelines.add(pipeline);
        return this;
    }
}
