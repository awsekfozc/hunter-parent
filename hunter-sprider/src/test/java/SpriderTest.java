import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.spider.ExpandSpider;
import com.csair.csairmind.hunter.spider.processor.currency.ResourcesProcessor;
import com.csair.csairmind.hunter.spider.schedule.ResourceTaskScheduler;
import com.csair.csairmind.hunter.spider.vo.ResourceTask;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.scheduler.RedisScheduler;

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
        resource_taskRule.put("url", " http://news.sogou.com/news?mode=1&query=%C4%CF%BA%BD&sut=5662&lkt=3%2C1495522105923%2C1495522106115&sst0=1495522109354&page=1&w=01029901&dr=1");
        resource_taskRule.put("details_url_xpath", "//div[@class=\"r-info r-info2\"]");
        resource_taskRule.put("details_url_reg", "");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
    }

    @Test
    public void testResourceProcessor() {
        Jedis jedis = pool.getResource();
        jedis.lpush(SprderConstants.R_RESOURCE_TASK, JSON.toJSONString(resource_taskRule));
        ResourceTask task = JSON.parseObject(jedis.lpop(SprderConstants.R_RESOURCE_TASK), ResourceTask.class);
        System.out.println(task);
        ExpandSpider.create(new ResourcesProcessor(task))
                .setScheduler(new ResourceTaskScheduler(pool))
                .setStartRequest(task.getUrl()).run();
    }
}
