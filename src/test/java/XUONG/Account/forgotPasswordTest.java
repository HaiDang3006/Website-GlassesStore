package XUONG.Account;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static XUONG.Utils.ExcelUtil.*;

public class forgotPasswordTest {

    List<Object[]> testResults = new ArrayList<>();
    private static String url = "http://localhost:8080/forgotPass";
    private String fileName = "forgotPasswordData.xlsx";
    private static WebDriver driver;


    @BeforeMethod
    public static void setUpBeforeClass() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
    }

    @Test(dataProvider = "forgotPasswordData")
    public void forgotPasswordTest(String testCaseId, String testScenario, String testCaseDes, String preCondition, List<String> testSteps, List<String> testData, String expected) throws InterruptedException {

        try {
            String email = testData.size() > 0 ? testData.get(0).split("Email: ")[1] : "";
            expected = expected.split("\"")[1].split("\"")[0];
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement emailField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("email"))
            );
            emailField.sendKeys(email);
            WebElement forgotPwButton = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("submit-btn"))
            );
            forgotPwButton.click();
            List<WebElement> messageErrorElement = driver.findElements(By.name("messageError"));
            List<WebElement> textH2Element = driver.findElements(By.cssSelector(".text-center.mb-4"));

            String result = "";
            String status = "";
            String imageError = "";
            Thread.sleep(2000);
            if(messageErrorElement.size() > 0) {
                result = messageErrorElement.get(0).getText();
            }
            else if(textH2Element.size() > 0) {

                if(testData.toString().contains("OTP")){
                    String otpCode = testData.size() > 0 ? testData.get(1).split("OTP:")[1] : "";
                    String password = testData.size() > 0 ? testData.get(2).split("Password:")[1] : "";
                    String confirmPassword = testData.size() > 0 ? testData.get(3).split("ConfirmPassword:")[1] : "";
                    WebElement otpCodeField = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.id("otpCode"))
                    );
                    otpCodeField.sendKeys(otpCode.trim());
                    WebElement newPasswordField = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.id("newPassword"))
                    );
                    newPasswordField.sendKeys(password.trim());
                    WebElement confirmPasswordField = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.id("confirmPassword"))
                    );
                    confirmPasswordField.sendKeys(confirmPassword.trim());
                    WebElement buttonChangePw = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-primary.w-100"))
                    );

                    buttonChangePw.click();


                    List<WebElement> messageErrorOtp = driver.findElements(By.id("messageErrorOtp"));
                    List<WebElement> messageErrorNewPassword = driver.findElements(By.id("messageErrorNewPassword"));
                    List<WebElement> messageErrorConfirmPassword = driver.findElements(By.id("messageErrorConfirmPassword"));

                    Thread.sleep(2000);

                    if(expected.contains("http")){
                        result = driver.getCurrentUrl();

                    }
                    else if(messageErrorOtp.size() > 0) {
                        result = messageErrorOtp.get(0).getText();
                    }
                    else if(messageErrorNewPassword.size() > 0) {
                        result = messageErrorNewPassword.get(0).getText();
                    }
                    else if(messageErrorConfirmPassword.size() > 0) {
                        result = messageErrorConfirmPassword.get(0).getText();
                    }
                    else {
                        result = "Không có phần tử";
                    }

                }
                else {
                    result = textH2Element.get(0).getText();
                }

            }
            else {
                Assert.assertEquals(driver.getCurrentUrl(), expected);
            }
            try{
                Assert.assertEquals(result, expected);
                status="PASS";
            } catch (Exception e) {

            } finally {
                status = status.isEmpty() ? "FAIL" : status;
                if(status.equals("FAIL")){
                    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String pathScreenShotError = String.format("src/main/resources/images/ImageError_%s.png", timestamp);
                    FileUtils.copyFile(screenshot, new File(pathScreenShotError));
                    imageError = pathScreenShotError;
                }
                testResults.add(new Object[]{testCaseId, testScenario, testCaseDes, preCondition, testSteps, testData, expected, result, status, imageError});
            }

        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @DataProvider(name = "forgotPasswordData")
    public Object[][] loginData() throws IOException {
        Workbook workbook = getFileExcel(fileName);
        Sheet sheet = workbook.getSheetAt(0);

        int numberOfRows = sheet.getPhysicalNumberOfRows();
        DataFormatter formatter = new DataFormatter();
        List<Object[]> data = new ArrayList<>();

        for (int i = 1; i < numberOfRows; i++) {
            int mergedRowsCount = getMergedRowCount(sheet, i);
            Row row = sheet.getRow(i);

            String testCaseId = (row.getCell(0) == null) ? null : formatter.formatCellValue(row.getCell(0));
            String testScenario = (row.getCell(1) == null) ? null : formatter.formatCellValue(row.getCell(1));
            String testCaseDes = (row.getCell(2) == null) ? null : formatter.formatCellValue(row.getCell(2));
            String preCondition = (row.getCell(3) == null) ? null : formatter.formatCellValue(row.getCell(3));

            List<String> testSteps = new ArrayList<>();

            for(int j = i; j < i+mergedRowsCount; j++) {
                testSteps.add(sheet.getRow(j).getCell(4).toString());
            }

            List<String> testData = new ArrayList<>();

            for(int j = i; j < i+mergedRowsCount; j++) {
                String testStep = formatter.formatCellValue(sheet.getRow(j).getCell(5));
                if(!testStep.isEmpty()){
                    testData.add(testStep);
                }
            }
            String expected = (row.getCell(6) == null) ? "" : formatter.formatCellValue(row.getCell(6));

            data.add(new Object[]{testCaseId, testScenario, testCaseDes, preCondition, testSteps, testData, expected});
            i += mergedRowsCount - 1;
        }

        workbook.close();

        return data.toArray(new Object[0][]);
    }

    @AfterSuite
    public void writeToExcel() throws IOException {
        writeExcel(testResults.toArray(new Object[0][0]), fileName);
    }

    @AfterMethod
    public static void tearDownAfterClass() throws Exception {
        driver.quit();
    }
    private static int getMergedRowCount(Sheet sheet, int rowIndex) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress mergedRegion : mergedRegions) {
            if (mergedRegion.isInRange(rowIndex, 0)) {
                return mergedRegion.getLastRow() - mergedRegion.getFirstRow() + 1;
            }
        }
        return 1;
    }

}
