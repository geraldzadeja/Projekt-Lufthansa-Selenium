package com.tealium.ecommerce.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String screenshotPath = ConfigReader.getScreenshotFolder() + fileName;

            File destinationFile = new File(screenshotPath);
            destinationFile.getParentFile().mkdirs();

            FileHandler.copy(sourceFile, destinationFile);
            logger.info("Screenshot captured: {}", screenshotPath);

            return screenshotPath;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    public static String captureFailureScreenshot(WebDriver driver, String testMethodName) {
        String screenshotPath = takeScreenshot(driver, testMethodName + "_FAILED");
        logger.info("Failure screenshot saved: {}", screenshotPath);
        return screenshotPath;
    }
}
