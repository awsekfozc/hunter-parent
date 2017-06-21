import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.common.plug.RedisServiceImpl;
import com.csair.csairmind.hunter.master.MasterBootstrap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_RESOURCE_TASK;

/**
 * Created by zhangcheng
 */
@RunWith(SpringRunner.class)
@Import(MasterBootstrap.class)
public class ProperTest {

    @Autowired
    RedisServiceImpl redisServiceImpl;

    @Test
    public void getHello() throws Exception {
        Map task = JSON.parseObject(redisServiceImpl.lpop(R_RESOURCE_TASK), Map.class);
        System.out.println(task);
    }

    Map<String, Object> resource_taskRule = new HashMap<String, Object>();

    @Before
    public void init() {
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
    }

    @Test
    public void testResourceProcessor() {
        redisServiceImpl.lpush(SprderConstants.R_RESOURCE_TASK, JSON.toJSONString(resource_taskRule));
    }
}
