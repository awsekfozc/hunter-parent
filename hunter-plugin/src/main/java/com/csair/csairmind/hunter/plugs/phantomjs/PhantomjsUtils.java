package com.csair.csairmind.hunter.plugs.phantomjs;

import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.vo.CookiePlugVo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by fate
 * 自动登陆获取cookies
 */
@Slf4j
public class PhantomjsUtils {

    private String phantomjsPath;
    private String scriptPath;

    public Map<String, String> getCookies(CookiePlugVo vo) throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        StringBuffer sbf = new StringBuffer();
        Runtime rt = Runtime.getRuntime();
        phantomjsPath = "G:\\phantomjs\\bin\\phantomjs.exe";
        scriptPath = "F:\\workspace\\hunter-parent\\hunter-plugin\\script\\generateCookies.js";
        String command = String.format("%s %s %s %s %s %s %s %s", phantomjsPath, scriptPath, vo.getUrl(), vo.getUsername_position(), vo.getPwd_position(), vo.getUsername(), vo.getPassword(), vo.getSub_position());
        log.info("执行phantomjs命令：" + command);
        Process p = rt.exec(command);
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String tmp = "";
        while ((tmp = br.readLine()) != null) {
            sbf.append(tmp + "\n");
        }
        System.out.println(sbf.toString());
        List list = JSON.parseObject(sbf.toString(), List.class);
        for (Object o : list) {
            Map map = (Map) o;
            resultMap.put(map.get("name").toString(), map.get("value").toString());
        }
        return resultMap;
    }

    public static void click(String username, String password) throws InterruptedException {
        System.setProperty("phantomjs.binary.path", "G:\\phantomjs\\bin");
        WebDriver driver = new PhantomJSDriver();
        driver.get("http://weibo.com/login.php");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000 * 5);
        driver.findElement(By.xpath("//*[@id=\"loginname\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"loginname\"]")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"pl_login_form\"]/div/div[3]/div[2]/div/input")).clear();
        driver.findElement(By.xpath("//*[@id=\"pl_login_form\"]/div/div[3]/div[2]/div/input")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"pl_login_form\"]/div/div[3]/div[6]/a")).click();
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie c : cookies) {
            System.out.println(c.getName() + "=" + c.getValue());
        }
        driver.close();
    }

    public static void main(String[] args) throws Exception {
        click("18007303287", "qwertsekfo1");
    }
}