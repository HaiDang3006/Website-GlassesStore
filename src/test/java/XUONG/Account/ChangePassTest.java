package XUONG.Account;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ChangePassTest {

    private static WebDriver driver;
    private static final String URL = "http://localhost:8080/home";

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(URL);

        driver.findElement(By.xpath("//a[@id='dropdownId']")).click();
        driver.findElement(By.xpath("//a[@href='/login']")).click();

        driver.findElement(By.name("email")).sendKeys("thanhNguyen@gmail.com");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.xpath("//button[contains(text(),'Đăng nhập')]")).click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//a[@id='dropdownId']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Đổi mật khẩu')]")).click();
    }

    @Test
    public void testChangePasswordSuccess() {

        driver.findElement(By.id("cu")).sendKeys("123456");
        driver.findElement(By.id("moi")).sendKeys("123456");
        driver.findElement(By.id("moi2")).sendKeys("123456");
        driver.findElement(By.xpath("//button[contains(text(),'Đổi mật khẩu')]")).click();

        // Chờ cho đến khi URL thay đổi (chuyển về trang login)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/login"));

        // Kiểm tra URL có đúng không
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "http://localhost:8080/login", "Không chuyển hướng đúng sau khi đổi mật khẩu thành công.");
    }


    @Test(dataProvider = "invalidPasswordData")
    public void testChangePasswordFail(String oldPass, String newPass, String confirmPass, String expectedError) {
        driver.findElement(By.id("cu")).sendKeys(oldPass);
        driver.findElement(By.id("moi")).sendKeys(newPass);
        driver.findElement(By.id("moi2")).sendKeys(confirmPass);
        driver.findElement(By.xpath("//button[contains(text(),'Đổi mật khẩu')]")).click();

        WebElement errorMessage = driver.findElement(By.id("error-message")); // Sửa lại cho đúng ID của thông báo lỗi
        Assert.assertTrue(errorMessage.isDisplayed(), "Thông báo lỗi không hiển thị");
        Assert.assertEquals(errorMessage.getText(), expectedError, "Thông báo lỗi không đúng");
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "invalidPasswordData")
    public Object[][] invalidPasswordData() {
        return new Object[][]{
                {"", "123456", "123456", "Vui lòng nhập mật khẩu cũ."}, // Thiếu mật khẩu cũ
                {"123456", "", "123456", "Vui lòng nhập mật khẩu mới."}, // Thiếu mật khẩu mới
                {"123456", "123456", "", "Vui lòng xác nhận mật khẩu mới."}, // Thiếu xác nhận mật khẩu
                {"123456", "12345", "123456", "Mật khẩu mới và xác nhận không khớp."}, // Mật khẩu mới và xác nhận không trùng
                {"123456", "12345", "12345", "Mật khẩu mới phải có ít nhất 6 ký tự."} // Mật khẩu mới quá ngắn
        };
    }

}
