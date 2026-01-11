package com.tealium.ecommerce.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);

    public static void initReports() {
        if (extent == null) {
            String reportPath = ConfigReader.getExtentReportPath();
            File reportFile = new File(reportPath);
            reportFile.getParentFile().mkdirs();

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Tealium Ecommerce Automation Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "Tealium Ecommerce Demo");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", ConfigReader.getBrowser());
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));

            logger.info("Extent Reports initialized");
        }
    }

    public static void createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
        logger.info("Test created in report: {}", testName);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void logInfo(String message) {
        getTest().log(Status.INFO, message);
        logger.info(message);
    }

    public static void logPass(String message) {
        getTest().log(Status.PASS, message);
        logger.info("PASS: {}", message);
    }

    public static void logFail(String message) {
        getTest().log(Status.FAIL, message);
        logger.error("FAIL: {}", message);
    }

    public static void logSkip(String message) {
        getTest().log(Status.SKIP, message);
        logger.warn("SKIP: {}", message);
    }

    public static void attachScreenshot(String screenshotPath) {
        try {
            getTest().addScreenCaptureFromPath(screenshotPath);
            logger.info("Screenshot attached to report: {}", screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to attach screenshot: {}", e.getMessage());
        }
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent Reports flushed");
        }
    }

    public static void removeTest() {
        extentTest.remove();
    }
}
