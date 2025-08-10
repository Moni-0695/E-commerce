package base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import util.ExtentManager;
import util.ScreenshotUtil;
import util.TestData;

import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent = ExtentManager.getExtentReports();
    protected static ThreadLocal<ExtentTest> tlTest = new ThreadLocal<>();

    @BeforeMethod
    public void setUp(Method method) {
        driver = DriverFactory.initDriver();
        driver.manage().window().maximize();
        driver.get(TestData.BASE_URL);

        // start test in extent
        tlTest.set(extent.createTest(method.getName()));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                String path = ScreenshotUtil.takeScreenshot(driver, result.getName());
                tlTest.get().fail(result.getThrowable());
                if (path != null) tlTest.get().addScreenCaptureFromPath(path);
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                tlTest.get().pass("Test passed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                tlTest.get().skip("Test skipped");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DriverFactory.quitDriver();
        }
    }

    @AfterSuite
    public void flush() {
        extent.flush();
    }
}