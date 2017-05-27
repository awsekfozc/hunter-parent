import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.common.vo.DetailsTask;
import com.csair.csairmind.hunter.common.vo.ResourceTask;
import com.csair.csairmind.hunter.spider.ExpandSpider;
import com.csair.csairmind.hunter.spider.distinct.ContentDistinct;
import com.csair.csairmind.hunter.spider.factory.DistinctFactory;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsListProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsSingleProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.ResourcesProcessor;
import com.csair.csairmind.hunter.spider.schedule.ResourceTaskScheduler;
import org.apache.http.HttpHost;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangcheng on 2017/5/23 0023.
 */
public class SpriderTest {

    Map<String, Object> resource_taskRule = new HashMap<String, Object>();
    JedisPool pool;

    @Before
    public void init() {
        //新浪新闻
//        resource_taskRule.put("url", "http://search.sina.com.cn/?q=%C4%CF%BA%BD&c=news&from=index&col=&range=&source=&country=&size=&time=&a=&page=1&pf=2131425449&ps=2134309112&dpc=1");
//        resource_taskRule.put("details_url_xpath", "//div[@class=\"r-info r-info2\"]");
//        resource_taskRule.put("details_url_reg", "");

        //搜狗新闻
//        resource_taskRule.put("url", "http://temp.163.com/special/00804KVA/cm_guonei.js?callback=data_callback");
//        resource_taskRule.put("details_url_xpath", "");
//        resource_taskRule.put("details_url_reg", "http://news.163.com/(\\d+)/(\\d+)/(\\d+)/(\\w+).html");
//        resource_taskRule.put("details_url_jpath", "");

//        //中国民航网
//        resource_taskRule.put("url", "http://www.caacnews.com.cn/tt/index_1.html");
//        resource_taskRule.put("details_url_xpath", "/html/body/table[2]/tbody/tr/td[2]/table/tbody/tr[2]");
//        resource_taskRule.put("details_url_reg", "");
//        resource_taskRule.put("details_url_jpath", "");

        //2.1.1.7中国民航局
//        resource_taskRule.put("url", "http://www.caac.gov.cn/XWZX/DFDT/index_1.html");
//        resource_taskRule.put("details_url_xpath", "");
//        resource_taskRule.put("details_url_reg", "http://www.caac.gov.cn/XWZX/DFDT/(\\d+)/t(\\w+).html");
//        resource_taskRule.put("details_url_jpath", "");

//        //2.1.1.8交通运输部
//        resource_taskRule.put("url", "http://was.mot.gov.cn:8080/govsearch/searPage.jsp?page=1&indexPa=2&schn=252&curpos=%E4%B8%BB%E9%A2%98%E5%88%86%E7%B1%BB&sinfo=252&surl=zfxxgk/");
//        resource_taskRule.put("details_url_xpath", "");
//        resource_taskRule.put("details_url_reg", "http://zizhan.mot.gov.cn/zfxxgk/(\\w+)/(\\w+)/(\\d+)/t(\\w+).html");
//        resource_taskRule.put("details_url_jpath", "");

//        //2.1.1.9中国民用机场协会
//        resource_taskRule.put("url", "http://www.chinaairports.org.cn/IndustryNews/index_1.htm");
//        resource_taskRule.put("details_url_xpath", "//*[@id=\"news\"]/div[3]/div[3]/div[2]/div/div[2]");
//        resource_taskRule.put("details_url_reg", "");
//        resource_taskRule.put("details_url_jpath", "");

//        //2.1.1.10环球旅讯
//        resource_taskRule.put("url", "http://www.traveldaily.cn/airline/1/");
//        resource_taskRule.put("details_url_xpath", "/html/body/div[4]/div[2]/ul/li/div[2]/h2");
//        resource_taskRule.put("details_url_reg", "");
//        resource_taskRule.put("details_url_jpath", "");

//        //2.1.1.11中国民用航空局消费者事务中心
//        resource_taskRule.put("url", "http://www.caacca.org/rdgz/rdgz1/index_1.html");
//        resource_taskRule.put("details_url_xpath", "/html/body/table[8]/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td/table[2]/tbody/tr/td/table[2]/tbody/tr/td[3]");
//        resource_taskRule.put("details_url_reg", "");
//        resource_taskRule.put("details_url_jpath", "");

//        //2.1.1.12中国民用航空网
        resource_taskRule.put("url", "http://www.ccaonline.cn/news/top/page/2");
        resource_taskRule.put("details_url_xpath", "//*[@id=\"main-content\"]/div[1]/div[3]/article/h2");
        resource_taskRule.put("details_url_reg", "");
        resource_taskRule.put("details_url_jpath", "");
        resource_taskRule.put("distinct_type", 1);
        resource_taskRule.put("title_extract_rule", "//*[@id=\"the-post\"]/div/h1/span/text()");
        resource_taskRule.put("date_extract_rule", "//*[@id=\"the-post\"]/div/p/span[2]/text()");
        resource_taskRule.put("source_extract_rule", "");
        resource_taskRule.put("content_extract_rule", "//*[@id=\"the-post\"]/div/div[3]/tidyText()");
        resource_taskRule.put("data_source", "中国民用航空网-新闻头条");
        resource_taskRule.put("task_type", 1);



        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
    }

    @Test
    public void testResourceProcessor() {
        Jedis jedis = pool.getResource();
        jedis.lpush(SprderConstants.R_RESOURCE_TASK, JSON.toJSONString(resource_taskRule));
        ResourceTask task = JSON.parseObject(jedis.lpop(SprderConstants.R_RESOURCE_TASK), ResourceTask.class);
        System.out.println(task);
        ExpandSpider.create(new ResourcesProcessor(task), pool)
                .setScheduler(new ResourceTaskScheduler())
                .setStartRequest(task.getUrl())
                .setDistinct(DistinctFactory.getInstance(task.getDistinct_type()))
                .run();
    }

    @Test
    public void testDetailsSingleProcessor() {
        Jedis jedis = pool.getResource();
        DetailsTask task = JSON.parseObject(jedis.hget(SprderConstants.R_DETAILS_TASK, jedis.lpop(SprderConstants.R_DETAILS_TASK_KEY)), DetailsTask.class);
        System.out.println(task);
        ExpandSpider.create(new DetailsSingleProcessor(task), pool)
                .setScheduler(new ResourceTaskScheduler())
                .setStartRequest(task.getUrl())
                .setPipeline(new ConsolePipeline())
                .run();
    }

    @Test
    public void testDetailsListProcessor(){
        //2.1.1.3民航资源网-用户评论
        resource_taskRule.put("url", "https://www.capse.net/sound/comments");
        resource_taskRule.put("details_url_xpath", "");
        resource_taskRule.put("details_url_reg", "");
        resource_taskRule.put("details_url_jpath", "");
        resource_taskRule.put("distinct_type", 1);
        resource_taskRule.put("title_extract_rule", "/html/body/div[2]/dl/dd/h1/a/text()");
        resource_taskRule.put("date_extract_rule", "/html/body/div[2]/dl/dd/p[3]/text()");
        resource_taskRule.put("source_extract_rule", "");
        resource_taskRule.put("content_extract_rule", "/html/body/div[2]/dl/dd/p[1]/text()");
        resource_taskRule.put("data_source", "民航资源网-用户评论");
        resource_taskRule.put("task_type", 2);
        Jedis jedis = pool.getResource();
        jedis.lpush(SprderConstants.R_RESOURCE_TASK, JSON.toJSONString(resource_taskRule));
        DetailsTask task = JSON.parseObject(jedis.lpop(SprderConstants.R_RESOURCE_TASK), DetailsTask.class);
        System.out.println(task);
        ExpandSpider.create(new DetailsListProcessor(task), pool)
                .setStartRequest(task.getUrl())
                .setDistinct(new ContentDistinct())
                .setPipeline(new ConsolePipeline())
                .run();
    }

    @Test
    public void testSprider() {
        Spider spider = new Spider(new PageProcessor() {
            public void process(Page page) {
                System.out.println(page.getHtml());
            }

            public Site getSite() {
                return Site.me()
                        .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
                        .addHeader("Cookie","Hm_lvt_a5257df898f02b3e0889ce42109b2628=1492999687,1493023645,1493024323,1493861217; _ga=GA1.2.1820557420.1492743250; XSRF-TOKEN=eyJpdiI6InVjUnFHQzdINDNLMU1qMW1LWnpwZ2c9PSIsInZhbHVlIjoialplMHBxbWlYRVwvMWhWZmZhM1F3VVk5WDJoUEx5V2l2Nk1pRlwvbURHWFVzQ1NBWmpHZUlVWURKeW5iamRNV2hCbG5Zb2ltZDdFVzlDYUpubCsrVjEyQT09IiwibWFjIjoiNjQxMDlhMTIzNGYxMzRlMzk3ZTc5ZDY3ZTM3MzZlN2E3YzEzMDNkOGMzMjY0ZTIxMGRkMGMzMjFhNjE0MGI5OCJ9; laravel_session=eyJpdiI6IjZGTVwvekRRdmJTOU5iUldrUTF0S1dnPT0iLCJ2YWx1ZSI6Ik55R2NVYjM4R2pcL0U0bWNTVGp4Z2lQT1wvXC8xdTZtU3VOQmVLaTdaR1JCRURoMTRiM2tNRHNOMXlNNnNqSUJGbFhMa2pHNmVrYzQ5dk9QZXl2dEFaQVRRPT0iLCJtYWMiOiI1ZmJjYjdjMDFmNjQxM2UzZDNiNjhhMTE1MTI3ZDFhMDMyZDU3Y2U0OWVkMDI0MmNmZWNlZmQ2MWNmNTZjNzRhIn0%3D")
                        .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        ;
            }
        });
        spider.test("https://www.capse.net/sound/comments?page=1&limit=20");
    }
}
