package XUONG.QLSP;

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
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestProductDelete {


    private WebDriver driver;
    private String url = "http://localhost:8080/product";


    @BeforeMethod
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to(url);

        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("email")).sendKeys("a");
        driver.findElement(By.name("password")).sendKeys("a");
        driver.findElement(By.id("abcd")).click();
    }


    @Test
    public void updateProductPass() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

//        driver.findElement(By.xpath("//a[normalize-space()='Trang 3']")).click();

        driver.findElement(By.xpath("//a[@id='7']")).click();

        driver.findElement(By.xpath("//button[normalize-space()='Xóa']")).click();

        WebElement contentDelete = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='swal2-html-container']")));

        if (contentDelete.getText().equals("Xóa sản phẩm thành công")){
            Assert.assertEquals(contentDelete.getText(), "Xóa sản phẩm thành công");
            System.out.println("Test Pass");
        }else{
            Assert.fail("Test Fail");
        }

    }


    @AfterMethod
    public void closeBrowser() {
        driver.close();
    }

}


