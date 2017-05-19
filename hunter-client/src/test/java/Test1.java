
import com.csair.csairmind.hunter.client.TestBootstrap;
import com.csair.csairmind.hunter.common.config.AppValidateInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhangcheng on 2017/5/11 0011.
 */
@RunWith(SpringRunner.class)
@Import(TestBootstrap.class)
public class Test1 {

    @Autowired
    private AppValidateInfo appValidateInfo;

    @Test
    public void getHello() throws Exception {
        System.out.println(appValidateInfo);
        Assert.assertEquals(appValidateInfo.getAppKey(), "1001");
        Assert.assertEquals(appValidateInfo.getAppSecret(), "e470f665d1fad185234a27596d7b9a43");
    }
}
