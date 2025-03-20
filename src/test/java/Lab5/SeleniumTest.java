package Lab5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    private WebDriver driver;
    private String url = "http://localhost:8080/login";



    @Test(dataProvider = "dp")
    public void testLoginFail(String email, String password) {
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("abcd")).click();

        boolean message = driver.findElement(By.xpath("//span[@class='text-danger']")).isDisplayed();

        WebElement contentMessage = driver.findElement(By.xpath("//span[@class='text-danger']"));

        if(message){
            Assert.assertEquals(contentMessage.getText(), "* Tài khoản hoặc mật khẩu không hợp lệ");
            System.out.println("Test Pass");
        }else{
            System.out.println("Test Fail");
        }

    }


    @Test
    public void loginSuccess(){
        driver.findElement(By.name("email")).sendKeys("a");
        driver.findElement(By.name("password")).sendKeys("a");
        driver.findElement(By.id("abcd")).click();

        String url = driver.getCurrentUrl();

        if(url.equals("http://localhost:8080/Admin")){
            System.out.println("Test Pass");
        }else {
            System.out.println("Test Fail");
        }

    }

    @BeforeMethod
    public void openBrowser(){

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.navigate().to(url);
    }


    @DataProvider
    public Object[][] dp(){
        return new Object[][]{
                new Object[]{"a", "a"},
                new Object[]{"", "Saine"},
                new Object[]{"", "haidang"},
        };
    }

    @AfterMethod
    public void closeBrowser() {
        driver.close();
    }


}
