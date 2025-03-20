package XUONG.Account;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class LoginTest {
    private WebDriver driver;
    private String url = "http://localhost:8080/login";

    @BeforeMethod
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to(url);
    }

    @Test
    public void login() {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("a");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("a");
        driver.findElement(By.xpath("//button[@id='abcd']")).click();

        String url = driver.getCurrentUrl();
        if(url.equals("http://localhost:8080/Admin")){
            System.out.println("Test pass");
        }else {
            System.out.println("Test fail");
        }
        Assert.assertEquals("http://localhost:8080/Admin", url);
    }

    @Test(dataProvider = "dp")
    public void loginFail(String email, String password) throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@id='abcd']")).click();

        boolean error = driver.findElement(By.xpath("//span[@class='text-danger']")).isDisplayed();
        WebElement contentError = driver.findElement(By.xpath("//span[@class='text-danger']"));
        if(error){
            if(contentError.equals("Tài khoản đã bị vô hiệu hóa ")){
                Assert.assertEquals(contentError.getText(), "Tài khoản đã bị vô hiệu hóa ");
            }else {
                Assert.assertEquals(contentError.getText(), "*Tài khoản hoặc mật khẩu không hợp lệ");
            }
            System.out.println("Test pass");
        }else{
            System.out.println("Test fail");
        }
    }

    @AfterMethod
    public void closeBrowser() {
        driver.close();
    }

    @DataProvider
    public Object[][] dp(){
        return new Object[][]{
                new Object[]{"thanhdp@gmail.com", "123uoi"},
                new Object[]{"thanhdppc08294@gmail.com", "123456"},
                new Object[]{"thanhd84@gmail.com", "123457"},
                new Object[]{"thanhdppc08294@gmail.com", ""},
                new Object[]{"thanh08294@gmail.com", ""},
                new Object[]{"", "123uio"},
        };
    }
}


