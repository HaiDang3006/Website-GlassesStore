package XUONG.UpdateInfoUser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
public class UpdateInfoUser {


    private WebDriver driver;
    private String url = "http://localhost:8080/login";


    @Test(dataProvider = "dataFail")
    public void UpdateInfoTestFaill(String name, String email, String image) {

        File file = new File(image);
        driver.findElement(By.xpath("//input[@id='image']")).sendKeys(file.getAbsolutePath());

        WebElement xpathName = driver.findElement(By.xpath("//input[@name='name']"));
        WebElement xpathEmail = driver.findElement(By.xpath("//input[@name='email']"));
        WebElement xpathImage = driver.findElement(By.xpath("//input[@id='image']"));
        WebElement xpathButtonUpdate = driver.findElement(By.xpath("//button[contains(text(),'Cập nhật thông tin')]"));

        if (image.isEmpty()) {
            Assert.fail("Đường dẫn ảnh không được để trống!");
        }
        if(!Files.exists(Paths.get(image))) {
            Assert.assertTrue(isImagePathValid(image), "Ảnh không tồn tại!");
        }

        xpathName.clear();
        xpathEmail.clear();

        xpathName.sendKeys(name);
        xpathEmail.sendKeys(email);
        xpathImage.sendKeys(file.getAbsolutePath());

        xpathButtonUpdate.click();

        boolean checkError = driver.findElement(By.xpath("//div[@id='error-message']")).isDisplayed();

        if(checkError){
            String message = driver.findElement(By.xpath("//div[@id='error-message']")).getText();


            if (name.isEmpty() || email.isEmpty() ){

                Assert.assertEquals(message, "Họ và tên không được để trống!");
            }

            if (message.equals("Email không đúng định dạng!")){
                Assert.assertEquals(message, "Email không đúng định dạng!");
            }
        }
    }

    public boolean isImagePathValid(String imagePath) {
        return Files.exists(Paths.get(imagePath));
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.navigate().to(url);

        this.goToAddInfoUser();
    }

    public void goToAddInfoUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']")));
        emailInput.sendKeys("HaiDang@gmail.com");

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='password']")));
        passwordInput.sendKeys("haidang01");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Đăng nhập')]")));
        loginButton.click();

        WebElement dropdownToggle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='text-end']")));

        Actions action = new Actions(driver);
        action.moveToElement(dropdownToggle).perform();

        WebElement myAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-bs-toggle='modal']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myAccountLink);

    }


    @AfterMethod
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }


    @DataProvider
    public Object[][] dataFail() {
        return new Object[][]{
                //trống 1
                {"", "loc01@gmail.com", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},
                {"vanloc", "", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},
                {"vanloc", "loc01@gmail.com", ""},

                //trống 2
                {"", "", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},
                {"", "loc01@gmail.com", ""},
                {"vanloc", "", ""},

                //trống 3
                {"", "", ""},

                //sai 1
                {"ss", "loc01@gmail.com", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},
                {"vanloc", "loc01@gmail", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},
                {"vanloc", "loc01@gmail", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},

                // Sai 2
                {"ss", "loc01@gmail", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},
                {"ss", "loc01@gmail.com", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},
                {"vanloc", "loc01@gmail", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},

                // sai 3
                {"ss", "loc01@gmail", "C:\\Users\\ASUS\\Pictures\\Áo Khoát đỏ caro"},
        };
    }


}
