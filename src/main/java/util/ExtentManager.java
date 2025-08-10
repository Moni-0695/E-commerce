package util;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setDocumentTitle("E-Commerce Automation Report");
            spark.config().setReportName("Demoblaze Automation");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Author", "Automation");
            extent.setSystemInfo("URL", TestData.BASE_URL);
        }
        return extent;
    }
}