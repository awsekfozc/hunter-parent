package selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import us.codecraft.webmagic.Site;

import java.util.Set;

public class WeiBoMSelenium {

    final static String DRIVER_PATH = "C:/Program Files/Mozilla Firefox/firefox.exe";
    final static String WEB_PATH = "https://passport.weibo.cn/signin/login";

    public static void main(String[] args) throws InterruptedException {

        String username = "18007303287";
        String password = "qwertsekfo1";
        WeiBoMSelenium.click(Site.me(),username, password);
        System.out.println("end");
    }
    /**
     * 获取cookie
     * @param username
     * @param password
     * @return
     * @throws InterruptedException
     */
    public static Site click(Site site,String username, String password) throws InterruptedException {

        System.setProperty("webdriver.firefox.bin", DRIVER_PATH);

        WebDriver driver = new FirefoxDriver();
        driver.get(WEB_PATH);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000*2);
        driver.findElement(By.xpath("//*[@id=\"loginName\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"loginName\"]")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"loginPassword\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"loginPassword\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"loginAction\"]")).click();
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie c : cookies) {
            System.out.println(c.getDomain()+"  "+c.getName()+"  "+c.getValue());
            site.addCookie(c.getName(),c.getValue());
        }
        driver.close();
        return site;
    }
}