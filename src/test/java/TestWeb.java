import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @Author:xishuya
 * @Date:2020/11/15 5:22 PM
 */
public class TestWeb {

    WebDriver driver = new ChromeDriver();


    //获取登录的cookie---复用cookie的方式
    @Test
    void testLoginWriteCookie() throws IOException, InterruptedException {

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");

        Thread.sleep(20000);

        Set<Cookie> cookies = driver.manage().getCookies();


        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("cookie.yaml"), cookies);


    }

    @Test
    void testAddMember() throws IOException {

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference typeReference = new TypeReference<List<HashMap<String, Object>>>() {
        };

        List<HashMap<String, Object>> cookies = mapper.readValue(new File("cookie.yaml"), typeReference);

        cookies.forEach(cookieMap->{
            driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(),cookieMap.get("value").toString()));
        });

        driver.navigate().refresh();

        driver.findElement(By.id("menu_index")).click();

        driver.findElement(By.cssSelector(".ww_indexImg_AddMember")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        driver.findElement(By.id("username")).sendKeys("ceshi001");
        driver.findElement(By.id("memberAdd_acctid")).sendKeys("ceshi001");
        driver.findElement(By.id("memberAdd_phone")).sendKeys("15051999999");
        driver.findElement(By.xpath("//*[@id=\"js_contacts114\"]/div/div[2]/div/div[4]/div/form/div[3]/a[2]")).click();


    }



   /* @AfterEach
    void quit(){
        driver.quit();
    }*/

}
