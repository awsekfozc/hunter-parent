package com.csair.csairmind.hunter.spider;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangcheng on 2017/5/23 0023.
 */
@Slf4j
public class ExpandSpider implements Task, Runnable {

    protected Downloader downloader;
    protected List<Pipeline> pipelines = new ArrayList();
    protected PageProcessor pageProcessor;
    protected List<Request> startRequests;
    protected Site site;
    protected String uuid;
    protected Scheduler scheduler = new QueueScheduler();
    protected CountableThreadPool threadPool;
    protected int threadNum = 1;
    protected AtomicInteger stat = new AtomicInteger(0);
    protected boolean spawnUrl = true;
    private Request startRequest;

    public static ExpandSpider create(PageProcessor pageProcessor) {
        return new ExpandSpider(pageProcessor);
    }

    public ExpandSpider(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        this.site = pageProcessor.getSite();
        this.startRequests = pageProcessor.getSite().getStartRequests();
    }

    @Override
    public void run() {
        this.checkRunningStat();
        this.initComponent();
        log.info("Spider " + this.getUUID() + " started!");
        if (startRequest != null) {
            try {
                ExpandSpider.this.processRequest(startRequest);
            } catch (Exception var5) {
                log.error("process request " + startRequest + " error", var5);
            } finally {
                this.close();
            }
        }
    }


    /***
     * 任务处理
     * @param request
     */
    protected void processRequest(Request request) {
        Page page = this.downloader.download(request, this);
        if (page == null) {
            this.sleep(this.site.getSleepTime());
        } else if (page.isNeedCycleRetry()) {
            this.extractAndAddRequests(page, true);
            this.sleep(this.site.getRetrySleepTime());
        } else {
            this.pageProcessor.process(page);
            this.extractAndAddRequests(page, this.spawnUrl);
            if (!page.getResultItems().isSkip()) {
                Iterator var3 = this.pipelines.iterator();

                while (var3.hasNext()) {
                    Pipeline pipeline = (Pipeline) var3.next();
                    pipeline.process(page.getResultItems(), this);
                }
            }

            request.putExtra("statusCode", Integer.valueOf(page.getStatusCode()));
            this.sleep(this.site.getSleepTime());
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
     * 上传新资源
     * @param page
     * @param spawnUrl
     */
    protected void extractAndAddRequests(Page page, boolean spawnUrl) {
        if (spawnUrl && CollectionUtils.isNotEmpty(page.getTargetRequests())) {
            Iterator var3 = page.getTargetRequests().iterator();

            while (var3.hasNext()) {
                Request request = (Request) var3.next();
                this.addRequest(request);
            }
        }

    }

    /***
     * 添加资源
     * @param request
     */
    private void addRequest(Request request) {
        if (this.site.getDomain() == null && request != null && request.getUrl() != null) {
            this.site.setDomain(UrlUtils.getDomain(request.getUrl()));
        }
        this.scheduler.push(request, this);
    }

    /***
     * 检查爬虫是否正在运行
     */
    private void checkRunningStat() {
        int statNow;
        do {
            statNow = this.stat.get();
            if (statNow == 1) {
                throw new IllegalStateException("Spider is already running!");
            }
        } while (!this.stat.compareAndSet(statNow, 1));

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

    public ExpandSpider setScheduler(Scheduler scheduler) {
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
}
