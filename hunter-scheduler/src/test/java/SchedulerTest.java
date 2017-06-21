import com.alibaba.fastjson.JSON;
import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_TASK_QUEUE;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcheng
 */
public class SchedulerTest {
    Map<String, Object> task = new HashMap<String, Object>();
    JedisPool pool;
    public Map<String, Object> getPageTask(){
        task.put("url", "http://www.ccaonline.cn/news/top/");
        task.put("page_reg", "http://www.ccaonline.cn/news/top/page/%d");//分页表达式
        task.put("details_url_xpath", "//*[@id=\"main-content\"]/div[1]/div[3]/article/h2");
        task.put("details_url_reg", "");
        task.put("details_url_jpath", "");
        task.put("distinct_type", 1);
        task.put("title_extract_rule", "//*[@id=\"the-post\"]/div/h1/span/text()");
        task.put("date_extract_rule", "//*[@id=\"the-post\"]/div/p/span[2]/text()");
        task.put("source_extract_rule", "");
        task.put("content_extract_rule", "//*[@id=\"the-post\"]/div/div[3]/tidyText()");
        task.put("data_source", "中国民用航空网-新闻头条");
        task.put("task_type", 1);
        task.put("increment_rule", 120000);
        task.put("max_page_size", 50);
        task.put("search_wrods", "");
        task.put("task_id", "10001");
        task.put("request_time","2017-6-5 10:00:00");
        return task;
    }


    public Map<String, Object> getWrodTask(){
        task.put("url", "http://search.sina.com.cn/?q=%C4%CF%BA%BD&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page=1&pf=2131425470&ps=2134309112&dpc=1");
        task.put("page_reg", "http://search.sina.com.cn/?q=${word}&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page=${page}&pf=2131425470&ps=2134309112&dpc=1");//分页表达式
        task.put("details_url_xpath", "//*[@id=\"result\"]/div[4]");
        task.put("details_url_reg", "");
        task.put("details_url_jpath", "");
        task.put("distinct_type", 1);
        task.put("title_extract_rule", "//*[@id=\"artibodyTitle\"]/text()\t");
        task.put("date_extract_rule", "//*[@id=\"wrapOuter\"]/div/div[4]/span/text()\t");
        task.put("source_extract_rule", "");
        task.put("content_extract_rule", "//*[@id=\"artibody\"]/tidyText()");
        task.put("data_source", "新浪网");
        task.put("task_type", 1);
        task.put("increment_rule", 60000);
        task.put("max_page_size", 50);
        task.put("search_wrods", "南航,航空,南方航空");
        task.put("wrod_code", "GBK");
        task.put("task_id", "10002");
        task.put("request_time","2017-6-5 10:00:00");
        return task;
    }

    @Test
    public void addPageTask(){
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        Jedis jedis = pool.getResource();
        jedis.del(R_TASK_QUEUE);
        getPageTask();
        jedis.hset(R_TASK_QUEUE, task.get("task_id").toString(),JSON.toJSONString(task));
    }

    @Test
    public void addWrodTask(){
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        Jedis jedis = pool.getResource();
        getWrodTask();
        jedis.hset(R_TASK_QUEUE, task.get("task_id").toString(),JSON.toJSONString(task));
    }

}
