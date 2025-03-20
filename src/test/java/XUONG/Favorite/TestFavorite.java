package XUONG.Favorite;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestFavorite {

    private WebDriver driver;
    private String url = "http://localhost:8080/home";


    @BeforeMethod
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to(url);

    }

    @Test
    public void testFavoriteFaill() {
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement favoriteButton = driver.findElement(By.xpath("//i[@id='heart-7']"));
        actions.moveToElement(favoriteButton).perform();
        favoriteButton.click();

        boolean messageFavorite = driver.findElement(By.xpath("//h2[@id='swal2-title']")).isDisplayed();
        WebElement contentFavorite = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='swal2-html-container']")));

        if (messageFavorite) {
            Assert.assertEquals(contentFavorite.getText(), "Chức năng này cần phải đăng nhập");
            System.out.println("Test case: Kiểm tra khi chưa đăng nhập - Pass");
        } else {
            Assert.fail("Test Fail");
            System.out.println("Test case: Kiểm tra khi chưa đăng nhập - Fail");
        }

    }


    @Test
    public void testFavoritePass() {

        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("email")).sendKeys("thanhNguyen@gmail.com");
        driver.findElement(By.name("password")).sendKeys("123123");
        driver.findElement(By.id("abcd")).click();

        Actions actions = new Actions(driver);

        WebElement favoriteButton = driver.findElement(By.xpath("//i[@id='heart-7']"));
        actions.moveToElement(favoriteButton).perform();

        boolean isAlreadyFavorite = favoriteButton.getAttribute("class").contains("text-danger");

        favoriteButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        if (isAlreadyFavorite) {
            wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(favoriteButton, "class", "text-danger")));
            boolean isStillFavorite = favoriteButton.getAttribute("class").contains("text-danger");
            Assert.assertFalse(isStillFavorite, "Test case: Không thể bỏ yêu thích - Fail");
            System.out.println(isStillFavorite ? "Test case: Không thể bỏ yêu thích - Fail"
                    : "Test case: Đã bỏ yêu thích thành công - Pass");
        } else {
            wait.until(ExpectedConditions.attributeContains(favoriteButton, "class", "text-danger"));
            boolean isFavoriteNow = favoriteButton.getAttribute("class").contains("text-danger");
            Assert.assertTrue(isFavoriteNow, "Test case: Không thể thêm vào yêu thích - Fail");
            System.out.println(isFavoriteNow ? "Test case: Đã thêm vào yêu thích thành công - Pass"
                    : "Test case: Không thể thêm vào yêu thích - Fail");
        }
    }

    @AfterMethod
    public void closeBrowser() {
        driver.close();
    }

}
