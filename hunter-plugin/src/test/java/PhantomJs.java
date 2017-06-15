import com.csair.csairmind.hunter.common.vo.CookiePlugVo;
import com.csair.csairmind.hunter.plugs.phantomjs.PhantomjsUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by fate
 */
public class PhantomJs {

    @Test
    public void testGit() throws Exception {
        Runtime rt = Runtime.getRuntime();
        String command = "G:\\phantomjs\\bin\\phantomjs.exe F:\\workspace\\hunter-parent\\hunter-plugin\\script\\git.js";
        Process p = rt.exec(command);
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String tmp = "";
        while ((tmp = br.readLine()) != null) {
            sbf.append(tmp + "\n");
        }
        System.out.println(sbf);
    }

    @Test
    public void testGitContent() throws Exception {
        Runtime rt = Runtime.getRuntime();
        String command = "G:\\phantomjs\\bin\\phantomjs.exe F:\\workspace\\hunter-parent\\hunter-plugin\\script\\gitContent.js";
        Process p = rt.exec(command);
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String tmp = "";
        while ((tmp = br.readLine()) != null) {
            sbf.append(tmp + "\n");
        }
        System.out.println(sbf);
    }

    @Test
    public void testGetCookies() {
        //http://weibo.com/login.php #loginname input[name='password'] 18007303287 qwertsekfo1 span[node-type='submitStates']
        try {
            CookiePlugVo vo = new CookiePlugVo();
            vo.setPassword("qwertsekfo1");
            vo.setPwd_position("input[name='password']");
            vo.setSub_position(".W_btn_a");
            vo.setUrl("http://weibo.com/login.php");
            vo.setUsername("18007303287");
            vo.setUsername_position("#loginname");
            Map<String, String> map = new PhantomjsUtils().getCookies(vo);
            for (String key : map.keySet()) {
                System.out.println(key + "=" + map.get(key));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
