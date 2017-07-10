import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import com.csair.csairmind.hunter.common.vo.ResourceRule;
import com.csair.csairmind.hunter.spider.ExpandSpider;
import com.csair.csairmind.hunter.spider.distinct.ContentDistinct;
import com.csair.csairmind.hunter.spider.factory.DistinctFactory;
import com.csair.csairmind.hunter.spider.increment.ResourceTaskIncrement;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsListProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsSingleProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.ResourcesProcessor;
import com.csair.csairmind.hunter.spider.schedule.ResourceTaskScheduler;
import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
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
//        Jedis jedis = pool.getResource();
//        jedis.lpush(SprderConstants.R_RESOURCE_TASK, JSON.toJSONString(resource_taskRule));
        String ruleStr = "{\n" +
                "    \"content_extract_rule\": \"//*[@id=\\\"artibody\\\"]/tidyText()\",\n" +
                "    \"data_source\": \"新浪网\",\n" +
                "    \"date_extract_rule\": \"//*[@id=\\\"wrapOuter\\\"]/div/div[4]/span/text()\\t\",\n" +
                "    \"details_url_jpath\": \"\",\n" +
                "    \"details_url_reg\": \"\",\n" +
                "    \"details_url_xpath\": \"//div[@class=\\\"r-info r-info2\\\"]\",\n" +
                "    \"distinct_type\": \"1\",\n" +
                "    \"increment_rule\": 60000,\n" +
                "    \"max_page_num\": \"50\",\n" +
                "    \"now_page_mun\": \"1\",\n" +
                "    \"paging_reg\": \"http://search.sina.com.cn/?q=%s&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page=${d}&pf=2131425470&ps=2134309112&dpc=1\",\n" +
                "    \"request_time\": \"2017-06-05 15:10:33\",\n" +
                "    \"search_wrods\": \"南航,航空,南方航空\",\n" +
                "    \"source_extract_rule\": \"\",\n" +
                "    \"task_id\": \"10002\",\n" +
                "    \"is_proxy\": \"1\",\n" +
                "    \"task_type\": \"1\",\n" +
                "    \"title_extract_rule\": \"//*[@id=\\\"artibodyTitle\\\"]/text()\\t\",\n" +
                "    \"url\": \"http://search.sina.com.cn/?q=%C4%CF%BA%BD&range=all&c=news&sort=time\"\n" +
                "}";
        ResourceRule task = JSON.parseObject(ruleStr, ResourceRule.class);
        ExpandSpider.create(task, pool)
                .run();
    }

    @Test
    public void testDetailsSingleProcessor() {
        Jedis jedis = pool.getResource();
        DetailsRule task = JSON.parseObject(jedis.hget(SprderConstants.R_DETAILS_TASK, jedis.lpop(SprderConstants.R_DETAILS_TASK_KEY)), DetailsRule.class);
        System.out.println(task);
        ExpandSpider.create(task, pool).run();
    }

    @Test
    public void testDetailsListProcessor() {
        //2.1.1.3民航资源网-用户评论
        resource_taskRule.put("url", "https://www.capse.net/sound/comments");
        resource_taskRule.put("details_url_xpath", "");
        resource_taskRule.put("details_url_reg", "");
        resource_taskRule.put("details_url_jpath", "");
        resource_taskRule.put("distinct_type", 1);
        resource_taskRule.put("title_extract_rule", "/html/body/div[2]/dl/dd/h1/a/text()");
        resource_taskRule.put("date_extract_rule", "/html/body/div[2]/dl/dd/p[3]/text()");
        resource_taskRule.put("source_extract_rule", "");
        resource_taskRule.put("content_extract_rule", "/h" +
                "tml/body/div[2]/dl/dd/p[1]/text()");
        resource_taskRule.put("data_source", "民航资源网-用户评论");
        resource_taskRule.put("task_type", 2);
        Jedis jedis = pool.getResource();
        jedis.lpush(SprderConstants.R_RESOURCE_TASK, JSON.toJSONString(resource_taskRule));
        DetailsRule task = JSON.parseObject(jedis.lpop(SprderConstants.R_RESOURCE_TASK), DetailsRule.class);
        System.out.println(task);
        ExpandSpider.create(task, pool).run();
    }

    @Test
    public void testSprider() {
        Spider spider = new Spider(new PageProcessor() {
            public void process(Page page) {
                System.out.println(page.getHtml());
            }

            public Site getSite() {
                return Site.me();
            }
        });
        spider.test("https://laod.cn/hosts/2017-google-hosts.html/comment-page-6");
    }

    @Test
    public void testResourceSingeTask() {
        String taskStr = "{\"content_extract_rule\":\"//*[@id=\\\"artibody\\\"]/tidyText()\",\"data_source\":\"新浪网\",\"date_extract_rule\":\"//*[@id=\\\"wrapOuter\\\"]/div/div[4]/span/text()\\t\",\"details_url_jpath\":\"\",\"details_url_reg\":\"\",\"details_url_xpath\":\"//*[@id=\\\"result\\\"]/div[4]\",\"distinct_type\":\"1\",\"increment_rule\":60000,\"max_page_size\":\"50\",\"page_reg\":\"http://search.sina.com.cn/?q=%s&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page=%d&pf=2131425470&ps=2134309112&dpc=1\",\"request_time\":\"2017-06-05 15:02:46\",\"search_wrods\":\"南航,航空,南方航空\",\"source_extract_rule\":\"\",\"task_id\":\"10002\",\"task_type\":\"1\",\"title_extract_rule\":\"//*[@id=\\\"artibodyTitle\\\"]/text()\\t\",\"url\":\"http://search.sina.com.cn/?q=%C4%CF%BA%BD&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page=1&pf=2131425470&ps=2134309112&dpc=1\"}\n";
        ResourceRule task = JSON.parseObject(taskStr, ResourceRule.class);
        System.out.println(task);
        ExpandSpider.create(new ResourcesProcessor(), pool)
                .setScheduler(new ResourceTaskScheduler())
                .setStartRequest(task.getUrl(), task)
                .setDistinct(DistinctFactory.getInstance(task.getDistinct_type()))
                .run();
    }
}
