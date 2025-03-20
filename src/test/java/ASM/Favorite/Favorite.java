package ASM.Favorite;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class Favorite {

    private WebDriver driver;
    private String url = "http://localhost:8080/home";

    @BeforeMethod
    public void openBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to(url);

        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("email")).sendKeys("taikhoan1234@gmail.com");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.id("abcd")).click();
    }

    @Test
    public void favoriteTestPass(){
        WebElement iconHeart = driver.findElement(By.xpath("//i[@id='heart-2']"));
        new Actions(driver).scrollToElement(iconHeart).perform();
    }


}
