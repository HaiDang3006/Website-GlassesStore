package ASM;

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

import java.io.File;
import java.util.concurrent.TimeUnit;

public class CategoryTest {

    private WebDriver driver;
    private String url = "http://localhost:8080/category";

    @BeforeMethod
    public void openBrowser(){
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to(url);

        driver.findElement(By.name("email")).sendKeys("a");
        driver.findElement(By.name("password")).sendKeys("a");
        driver.findElement(By.id("abcd")).click();
    }


    @Test(dataProvider = "dp")
    public void addCategoryFail(String nameCate, String image) {

        driver.findElement(By.xpath("//a[contains(text(),'Thêm mới')]")).click();

        driver.findElement(By.xpath("//input[@id='nameInsert']")).sendKeys(nameCate);

        if (!image.isEmpty()) {
            File file = new File(image);
            driver.findElement(By.xpath("//input[@id='image']")).sendKeys(file.getAbsolutePath());
        }

        driver.findElement(By.xpath("//button[normalize-space()='Thêm']")).click();

        boolean message = driver.findElement(By.xpath("//small[@id='nameErrorInsert']")).isDisplayed();
        WebElement contentMessage = driver.findElement(By.xpath("//small[@id='nameErrorInsert']"));

        boolean imageError = driver.findElement(By.xpath("//span[@id='errorImage']")).isDisplayed();
        WebElement contentImageError = driver.findElement(By.xpath("//span[@id='errorImage']"));
        
        if (message || imageError) {

            if (contentMessage.getText().equals("Tên danh mục không được để trống!")) {
                Assert.assertEquals(contentMessage.getText(), "Tên danh mục không được để trống!");
                System.out.println("Test Pass: Tên danh mục không được để trống!");
            } else if (contentMessage.getText().equals("Tên danh mục phải có ít nhất 3 ký tự!")) {
                Assert.assertEquals(contentMessage.getText(), "Tên danh mục phải có ít nhất 3 ký tự!");
                System.out.println("Test Pass: Tên danh mục phải có ít nhất 3 ký tự!");
            } else if(contentMessage.getText().equals("Tên Category không được quá 50 ký tự!")){
                Assert.assertEquals(contentMessage.getText(), "Tên Category không được quá 50 ký tự!");
                System.out.println("Test Pass: Tên Category không được quá 50 ký tự!");
            } else {
                System.out.println("Test Fail: Tên sản phẩm đúng");
            }
            
            if (contentImageError.getText().equals("Vui lòng chọn ảnh.")) {
                Assert.assertEquals(contentImageError.getText(), "Vui lòng chọn ảnh.");
                System.out.println("Test Pass: Vui lòng chọn ảnh.");
            } else if (contentImageError.getText().equals("Chỉ chấp nhận file ảnh (.jpg, .jpeg, .png).")) {
                Assert.assertEquals(contentImageError.getText(), "Chỉ chấp nhận file ảnh (.jpg, .jpeg, .png).");
                System.out.println("Test Pass: Chỉ chấp nhận file ảnh (.jpg, .jpeg, .png).");
            } else {
                System.out.println("Test Fail: Hình ảnh đúng");
            }
        }
    }

    @Test
    public void addCategoryPass(){
        driver.findElement(By.xpath("//a[contains(text(),'Thêm mới')]")).click();
        driver.findElement(By.name("typeName")).sendKeys("sadgasdasfd");
        File file = new File("C:\\Users\\ADMIN\\Downloads\\Screenshot 2025-02-21 151147.png");
        driver.findElement(By.id("image")).sendKeys(file.getAbsolutePath());
        driver.findElement(By.xpath("//button[normalize-space()='Thêm']")).click();

        String url = driver.getCurrentUrl();
        if (url.equalsIgnoreCase("http://localhost:8080/category")) {
            Assert.assertEquals(url, "http://localhost:8080/category");
            System.out.println("Test Pass");
        } else {
            System.out.println("Test Fail");
        }

    }

    @AfterMethod
    public void closeBrowser() {
        driver.close();
    }

    @DataProvider
    public Object[][] dp(){
        return new Object[][]{
                new Object[]{"", ""},
                new Object[]{"aa", "C:\\Users\\ADMIN\\Downloads\\Screenshot 2025-02-21 151147.png"},
                new Object[]{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "C:\\Users\\ADMIN\\Downloads\\Screenshot 2025-02-21 151147.png"},
                new Object[]{"Kính áp tròng", ""},
                new Object[]{"Kính áp tròng", "C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp"},
        };
    }


}
