package com.csair.csairmind.hunter.spider;

import com.csair.csairmind.hunter.common.constant.DataConstants;
import com.csair.csairmind.hunter.common.util.SimHash;
import com.csair.csairmind.hunter.common.vo.Rule;
import com.csair.csairmind.hunter.plugs.proxy.IpProxyPulg;
import com.csair.csairmind.hunter.spider.distinct.ContentDistinct;
import com.csair.csairmind.hunter.spider.distinct.Distinct;
import com.csair.csairmind.hunter.spider.distinct.UrlDistinct;
import com.csair.csairmind.hunter.common.exception.NoGetReadyException;
import com.csair.csairmind.hunter.spider.factory.DistinctFactory;
import com.csair.csairmind.hunter.spider.factory.PipelineFactory;
import com.csair.csairmind.hunter.spider.factory.ProcessorFactory;
import com.csair.csairmind.hunter.spider.factory.RedisFactory;
import com.csair.csairmind.hunter.spider.increment.ResourceTaskIncrement;
import com.csair.csairmind.hunter.spider.increment.TaskIncrement;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsListProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsSingleProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.HunterPageProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.ResourcesProcessor;
import com.csair.csairmind.hunter.spider.schedule.DistinctScheduler;
import com.csair.csairmind.hunter.spider.schedule.ResourceTaskScheduler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangcheng
 * 爬虫启动类
 */
@Slf4j
public class ExpandSpider implements Task, Runnable {

    //网页下载方式
    private Downloader downloader;
    //数据持久化方式
    private List<Pipeline> pipelines = new ArrayList();
    //网页处理方式
    private HunterPageProcessor pageProcessor;
    //请求参数设置
    private Site site;
    private String uuid;
    //资源调度
    private DistinctScheduler scheduler;
    //增量资源调度
    private TaskIncrement increment;
    private int threadNum = 1;
    private AtomicInteger stat = new AtomicInteger(0);
    private boolean spawnUrl = true;
    private boolean spawnDistinct = true;
    //起始请求集合
    private Map<Request, Rule> startRequestMap = new HashMap<Request, Rule>();
    //去重方式
    private Distinct distinct;
    //超时时长
    private Integer timeOut = 10000;


    public static ExpandSpider create(HunterPageProcessor pageProcessor) {
        return new ExpandSpider(pageProcessor, null, null);
    }

    public static ExpandSpider create(HunterPageProcessor pageProcessor, JedisPool pool) {
        return new ExpandSpider(pageProcessor, pool, null);
    }

    public static ExpandSpider create(Rule rule, JedisPool pool) {
        HunterPageProcessor pageProcessor = ProcessorFactory.initProcessor(rule);
        return new ExpandSpider(pageProcessor, pool, rule);
    }

    public ExpandSpider(HunterPageProcessor pageProcessor, JedisPool pool, Rule rule) {
        RedisFactory.init(pool);
        if (rule != null) {
            HttpClientDownloader downloader = new HttpClientDownloader();
            if(rule.getIs_proxy() == 1){
                //代理设置
                IpProxyPulg proxy = new IpProxyPulg(RedisFactory.getInstance());
                downloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(proxy.getProxyHost(),proxy.getProxyPort(),proxy.getProxyUserName(),proxy.getProxyPassWord())));
            }
            this.setDownloader(downloader);
            this.setScheduler(new ResourceTaskScheduler());
            this.setIncrement(new ResourceTaskIncrement());
            this.setPipeline(PipelineFactory.initPipeline(rule));
            this.setStartRequest(rule);
            this.setDistinct(DistinctFactory.getInstance(rule.getDistinct_type()));
        }
        this.pageProcessor = pageProcessor;
        this.site = pageProcessor.getSite();
    }

    @Override
    public void run() {
        try {
            this.checkRunningStat();//检查组件是否齐全
            this.initComponent();//检查默认组件
            log.info("Spider " + this.getUUID() + " started!");
            for (Request request : startRequestMap.keySet()) {
                if (pageProcessor instanceof ResourcesProcessor)//解析任务处理
                    this.processResourceRequest(request, startRequestMap.get(request));
                else if (pageProcessor instanceof DetailsSingleProcessor)//详情单条任务处理
                    this.processDetailsSingleRequest(request, startRequestMap.get(request));
                else if (pageProcessor instanceof DetailsListProcessor)//详情List任务处理
                    this.processDetailsListRequest(request, startRequestMap.get(request));
            }
        } catch (Exception var5) {
            log.error("执行爬虫出错", var5);
        } finally {
            this.close();
        }
    }


    /***
     * 资源解析任务处理
     * @param request
     */
    protected void processResourceRequest(Request request, Rule rule) {
        Page page = this.downloader.download(request, this);
        if (page.isDownloadSuccess()) {
            this.pageProcessor.process(page, rule);
            this.extractAndAddRequests(page, this.spawnUrl, rule);
        }
    }

    /***
     * 解析单条详情任务处理
     * @param request
     */
    protected void processDetailsSingleRequest(Request request, Rule rule) {
        Page page = this.downloader.download(request, this);
        if (page == null) {
        } else {
            this.pageProcessor.process(page, rule);
            if (!page.getResultItems().isSkip()) {
                Iterator var3 = this.pipelines.iterator();
                while (var3.hasNext()) {
                    Pipeline pipeline = (Pipeline) var3.next();
                    pipeline.process(page.getResultItems(), this);
                }
            }
            if (spawnDistinct)
                this.scheduler.pushDistinctQueue(request.getUrl());//任务完成，保存去重信息
        }
    }

    /***
     * 解析列表详情任务处理
     * @param request
     */
    protected void processDetailsListRequest(Request request, Rule rule) {
        Page page = this.downloader.download(request, this);
        if (page == null) {
        } else {
            this.pageProcessor.process(page, rule);
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
                        distinctCode = SimHash.getSimCode(content);//生成内容签名
                        if (distinct.isDistinct(content))//如果存在URL，不添加到资源池中
                            continue;
                    }
                    while (var3.hasNext()) {
                        Pipeline pipeline = (Pipeline) var3.next();
                        pipeline.process(page.getResultItems(), this);
                    }
                    if (spawnDistinct)
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
        this.pageProcessor.getSite().setTimeOut(timeOut);
        this.downloader.setThread(this.threadNum);
    }

    /***
     * 上传新发现资源
     * @param page
     * @param spawnUrl
     */
    protected void extractAndAddRequests(Page page, boolean spawnUrl, Rule rule) {
        int i = 0;
        boolean isIncrement = true;
        if (spawnUrl && CollectionUtils.isNotEmpty(page.getTargetRequests())) {
            Iterator var3 = page.getTargetRequests().iterator();
            while (var3.hasNext()) {
                Request request = (Request) var3.next();
                if (distinct instanceof UrlDistinct && distinct != null) {
                    if (distinct.isDistinct(request.getUrl())) {//如果存在URL，不添加到资源池中
                        isIncrement = false;
                        continue;
                    }
                }
                this.scheduler.push(request, this);
                i++;
            }
            //如果新发现的URL都不存在,则新增下一页任务到任务池
            if (distinct instanceof UrlDistinct && isIncrement) {
                log.info("新增下一页资源任务{}", rule);
                this.increment.put(rule);
            }
        }
        log.info("新增资源 {} 个", i);
    }


    /***
     * 检查爬虫是否准备就绪
     */
    private void checkRunningStat() throws NoGetReadyException {
        if (startRequestMap.size() == 0)
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

    public Map<Request, Rule> getStartRequest() {
        return startRequestMap;
    }

    public ExpandSpider setStartRequest(String requestString, Rule rule) {
        this.startRequestMap.put(new Request(requestString), rule);
        return this;
    }

    public ExpandSpider setStartRequest(Rule rule) {
        this.startRequestMap.put(new Request(rule.getUrl()), rule);
        return this;
    }

    public ExpandSpider setStartRequest(Request startRequest, Rule rule) {
        this.startRequestMap.put(startRequest, rule);
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

    public ExpandSpider setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public ExpandSpider setSpawnDistinct(boolean spawnDistinct) {
        this.spawnDistinct = spawnDistinct;
        return this;
    }

    public ExpandSpider setIncrement(TaskIncrement increment) {
        this.increment = increment;
        return this;
    }
}
