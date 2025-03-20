package XUONG.Account;

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

public class RegisterTest {

    private WebDriver driver;
    private String url = "http://localhost:8080/register";

    @BeforeMethod
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to(url);
    }

    @Test(dataProvider = "dp")
    public void addProductFail(String fullname, String email, String password, String confirmPassword) {
        // Truyền dữ liệu vào ô input name Product
        driver.findElement(By.xpath("//input[@id='articleFullName']")).sendKeys(fullname);

        driver.findElement(By.xpath("//input[@id='articleEmail']")).sendKeys(email);

        driver.findElement(By.xpath("//input[@id='articlePassword']")).sendKeys(password);

        driver.findElement(By.xpath("//input[@id='confirm']")).sendKeys(confirmPassword);

        driver.findElement(By.xpath("//button[contains(text(),'Đăng ký')]")).click();

        boolean nameError = driver.findElement(By.xpath("//span[@id='errorName']")).isDisplayed();
        WebElement contentNameError = driver.findElement(By.xpath("//span[@id='errorName']"));

        boolean emaillError = driver.findElement(By.xpath("//span[@id='errorEmail']")).isDisplayed();
        WebElement contentEmaillError = driver.findElement(By.xpath("//span[@id='errorEmail']"));

        boolean passwordError = driver.findElement(By.xpath("//span[@id='errorPass1']")).isDisplayed();
        WebElement contentPasswordError = driver.findElement(By.xpath("//span[@id='errorPass1']"));

        boolean confirmError = driver.findElement(By.xpath("//span[@id='errorPass2']")).isDisplayed();
        WebElement contentConfirmError = driver.findElement(By.xpath("//span[@id='errorPass2']"));

        if (nameError || emaillError || passwordError || confirmError) {

            if (contentNameError.getText().equals("Vui lòng nhập họ và tên")) {
                Assert.assertEquals(contentNameError.getText(), "Vui lòng nhập họ và tên");
                System.out.println("Test Pass: Vui lòng nhập họ và tên");
            } else if (contentNameError.getText().equals("Họ và tên phải trên 3 ký tự")) {
                Assert.assertEquals(contentNameError.getText(), "Họ và tên phải trên 3 ký tự");
                System.out.println("Test Pass: Họ và tên phải trên 3 ký tự");
            } else if(contentNameError.getText().equals("Họ và tên phải dưới 255 ký tự")){
                Assert.assertEquals(contentNameError.getText(), "Họ và tên phải dưới 255 ký tự");
                System.out.println("Test Pass: Họ và tên phải dưới 255 ký tự");
            } else if(contentNameError.getText().equals("Họ và tên không chứa ký tự đặt biệt")){
                Assert.assertEquals(contentNameError.getText(), "Họ và tên không chứa ký tự đặt biệt");
                System.out.println("Test Pass: Họ và tên không chứa ký tự đặt biệt");
            } else {
                System.out.println("Test Fail: Họ và tên đúng");
            }

            if (contentEmaillError.getText().equals("Vui lòng nhập Email")) {
                Assert.assertEquals(contentEmaillError.getText(), "Vui lòng nhập Email");
                System.out.println("Test Pass: Vui lòng nhập Email");
            } else if (contentEmaillError.getText().equals("Email không hợp lệ")){
                Assert.assertEquals(contentEmaillError.getText(), "Email không hợp lệ");
                System.out.println("Test Pass: Email không hợp lệ");
            } else if (contentEmaillError.getText().equals("Email đã tồn tại trong hệ thống.")) {
                Assert.assertEquals(contentEmaillError.getText(), "Email đã tồn tại trong hệ thống.");
                System.out.println("Test Pass: Email đã tồn tại trong hệ thống.");
            } else {
                System.out.println("Test Fail: Email đúng");
            }

            if (contentPasswordError.getText().equals("Vui lòng nhập mật khẩu")) {
                Assert.assertEquals(contentPasswordError.getText(), "Vui lòng nhập mật khẩu");
                System.out.println("Test Pass: Vui lòng nhập mật khẩu");
            } else if (contentPasswordError.getText().equals("Mật khẩu không hợp lệ")){
                Assert.assertEquals(contentPasswordError.getText(), "Mật khẩu không hợp lệ");
                System.out.println("Test Pass: Mật khẩu không hợp lệ");
            } else {
                System.out.println("Test Fail: Mật khẩu đúng");
            }

            if (contentConfirmError.getText().equals("Vui lòng xác nhận mật khẩu")) {
                Assert.assertEquals(contentConfirmError.getText(), "Vui lòng xác nhận mật khẩu");
                System.out.println("Test Pass: Vui lòng xác nhận mật khẩu");
            } else if (contentConfirmError.getText().equals("Xác nhận mật khẩu không đúng")){
                Assert.assertEquals(contentConfirmError.getText(), "Xác nhận mật khẩu không đúng");
                System.out.println("Test Pass: Xác nhận mật khẩu không đúng");
            } else {
                System.out.println("Test Fail: Xác nhận mật khẩu đúng");
            }
        }
    }

    @Test
    public void registerSuccess() {

        // Truyền dữ liệu vào ô input name Product
        driver.findElement(By.xpath("//input[@id='articleFullName']")).sendKeys("Tài khoản 5");
        driver.findElement(By.xpath("//input[@id='articleEmail']")).sendKeys("taikhoan1234567@gmail.com");
        driver.findElement(By.xpath("//input[@id='articlePassword']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='confirm']")).sendKeys("123456");

        driver.findElement(By.xpath("//button[contains(text(),'Đăng ký')]")).click();

        String url = driver.getCurrentUrl();
        if (url.equalsIgnoreCase("http://localhost:8080/login")) {
            System.out.println("Test Pass: Đăng ký thành công");
        } else {
            Assert.assertEquals(url, "http://localhost:8080/login");
            System.out.println("Test Fail");
        }
    }

    @DataProvider
    public Object[][] dp() {
        return new Object[][]{

                // full name, email, pas1, pass2
                new Object[]{"", "", "", ""},

                new Object[]{"", "dangthpc08348@gmail.com", "123456", "123456"},

                new Object[]{"Trần Hải Đăng", "", "123456", "123456"},

                new Object[]{"Trần Hải Đăng", "dangthpc08348@gmail.com", "", "123456"},

                new Object[]{"Trần Hải Đăng", "dangthpc08348@gmail.com", "123456", ""},

                new Object[]{"An", "dangthpc08348@gmail.com", "123456", "123456"},

                new Object[]{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "dangthpc08348@gmail.com", "123456", "123456"},

                new Object[]{"Hải Đăng @@", "dangthpc08348@gmail.com", "123456", "123456"},

                new Object[]{"Trần Hải Đăng", "dangthpc08348gmail", "123456", "123456"},

                new Object[]{"Trần Hải Đăng", "dangthpc08348@gmail.com", "12", "123456"},

                new Object[]{"Trần Hải Đăng", "dangthpc08348@gmail.com",
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "123456"},

                new Object[]{"Trần Hải Đăng", "dangthpc08348@gmail.com", "123456", "999999"},
                new Object[]{"Trần Hải Đăng", "dangthpc08348@gmail.com", "123456", "123456"},

        };
    }

    @AfterMethod
    public void closeBrowser() {
        driver.close();
    }
}
