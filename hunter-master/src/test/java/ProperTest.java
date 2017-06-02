import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.common.plug.RedisServiceImpl;
import com.csair.csairmind.hunter.master.Bootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhangcheng
 */
@RunWith(SpringRunner.class)
@Import(Bootstrap.class)
public class ProperTest {

    @Autowired
    RedisServiceImpl redisServiceImpl;

    @Test
    public void getHello() throws Exception {
        redisServiceImpl.hset(SprderConstants.MACHINE_QUEUE_PREFIX, "11", "222");

    }
}
