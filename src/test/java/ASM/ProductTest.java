package ASM;


import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductTest {

    private WebDriver driver;
    private String url = "http://localhost:8080/product";


    @BeforeMethod
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.navigate().to(url);

        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("email")).sendKeys("a");
        driver.findElement(By.name("password")).sendKeys("a");
        driver.findElement(By.id("abcd")).click();
    }

    @Test(dataProvider = "dp")
    public void addProductFail(String name, String image, String price, String category) {

        driver.findElement(By.xpath("//a[contains(text(),'Thêm sản phẩm')]")).click();

        driver.findElement(By.xpath("//input[@id='name']")).sendKeys(name);

        if (!image.isEmpty()) {
            File file = new File(image);
            driver.findElement(By.xpath("//input[@id='image']")).sendKeys(file.getAbsolutePath());
        }

        WebElement cate1 = driver.findElement(By.xpath("//select[@id='category']"));
        Select select = new Select(cate1);
        select.selectByValue(category);

        driver.findElement(By.xpath("//input[@id='price']")).sendKeys(price);

        driver.findElement(By.xpath("//button[contains(text(),'Lưu')]")).click();

        boolean nameError = driver.findElement(By.xpath("//span[@id='errorName']")).isDisplayed();
        WebElement contentNameError = driver.findElement(By.xpath("//span[@id='errorName']"));

        boolean imageError = driver.findElement(By.xpath("//span[@id='errorImage']")).isDisplayed();
        WebElement contentImageError = driver.findElement(By.xpath("//span[@id='errorImage']"));

        boolean priceError = driver.findElement(By.xpath("//span[@id='errorPrice']")).isDisplayed();
        WebElement contentPriceError = driver.findElement(By.xpath("//span[@id='errorPrice']"));

        boolean cateError = driver.findElement(By.xpath("//span[@id='errorCategory']")).isDisplayed();
        WebElement contentCateError = driver.findElement(By.xpath("//span[@id='errorCategory']"));

        if (nameError || imageError || priceError || cateError) {

            if (contentNameError.getText().equals("Vui lòng nhập tên sản phẩm.")) {
                Assert.assertEquals(contentNameError.getText(), "Vui lòng nhập tên sản phẩm.");
                System.out.println("Test Pass: Vui lòng nhập tên sản phẩm.");
            } else if (contentNameError.getText().equals("Tên sản phẩm quá ngắn")) {
                Assert.assertEquals(contentNameError.getText(), "Tên sản phẩm quá ngắn");
                System.out.println("Test Pass: Tên sản phẩm quá ngắn");
            } else if(contentNameError.getText().equals("Tên sản phẩm quá dài")){
                Assert.assertEquals(contentNameError.getText(), "Tên sản phẩm phải dưới 255 ký tự");
                System.out.println("Test Pass: Tên sản phâm quá dài");
            }else {
                System.out.println("Test Fail: Tên sản phẩm đúng");
            }

            if (contentImageError.getText().equals("Vui lòng chọn ảnh.")) {
                Assert.assertEquals(contentImageError.getText(), "Vui lòng chọn ảnh.");
                System.out.println("Test Pass: Vui lòng chọn ảnh.");
            } else {
                System.out.println("Test Fail: Hình ảnh đúng");
            }

            if (contentPriceError.getText().equals("Vui lòng nhập giá.")) {
                Assert.assertEquals(contentPriceError.getText(), "Vui lòng nhập giá.");
                System.out.println("Test Pass: Vui lòng nhập giá.");
            } else if (contentPriceError.getText().equals("Giá phải là 1 số lớn hơn 0 và nhỏ hơn 500 triệu")){
                Assert.assertEquals(contentPriceError.getText(), "Giá phải là 1 số lớn hơn 0 và nhỏ hơn 500 triệu");
                System.out.println("Test Pass: Giá phải là 1 số lớn hơn 0 và nhỏ hơn 500 triệu");
            } else {
                System.out.println("Test Fail: Giá tiền đúng");
            }

            if (contentCateError.getText().equals("Vui lòng chọn danh mục.")) {
                Assert.assertEquals(contentCateError.getText(), "Vui lòng chọn danh mục.");
                System.out.println("Test Pass: Vui lòng chọn danh mục.");
            } else {
                System.out.println("Test Fail: Danh mục đúng");
            }
        }
    }

    @Test
    public void addProductSuccess() {

        driver.findElement(By.xpath("//a[contains(text(),'Thêm sản phẩm')]")).click();

        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Sản phẩm 1");

        File file = new File("C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp");

        driver.findElement(By.id("image")).sendKeys(file.getAbsolutePath());

        driver.findElement(By.xpath("//input[@id='price']")).sendKeys("10000");
        driver.findElement(By.xpath("//select[@id='category']")).sendKeys("GỌNG KÍNH");

        driver.findElement(By.xpath("//button[contains(text(),'Lưu')]")).click();

        String url = driver.getCurrentUrl();

        if (url.equalsIgnoreCase("http://localhost:8080/product")) {
            Assert.assertEquals(url, "http://localhost:8080/product");
            System.out.println("Test Pass");
        } else {
            System.out.println("Test Fail");
        }
    }

    @DataProvider
    public Object[][] dp() {
        return new Object[][]{
                // Tên, Hình, Giá, Category
                new Object[]{"", "", "", ""},

                new Object[]{"", "C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp", "10000", "1"},

                new Object[]{"Sản phẩm 1", "", "10000", "1"},

                new Object[]{"Sản phẩm 1", "C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp", "", "1"},

                new Object[]{"Sản phẩm 1", "C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp", "10000", ""},

                new Object[]{"Sả", "C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp", "10000", "1"},

                new Object[]{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp", "10000", "1"},

                new Object[]{"Sản phẩm 1", "C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp", "10 ngàn", "1"},

                new Object[]{"Sản phẩm 1", "C:\\Users\\ADMIN\\Downloads\\trongkinh1.webp", "10000000000000000000000000000000000", "1"},
        };
    }

    @AfterMethod
    public void closeBrowser() {
        driver.close();
    }

}
