package com.tealium.ecommerce.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
            logger.info("Configuration file loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", e.getMessage());
            throw new RuntimeException("Configuration file not found at: " + CONFIG_FILE_PATH);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            logger.debug("Retrieved property - Key: {}, Value: {}", key, value);
            return value.trim();
        } else {
            logger.warn("Property not found for key: {}", key);
            throw new RuntimeException("Property not found: " + key);
        }
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless.mode"));
    }

    public static boolean shouldMaximizeWindow() {
        return Boolean.parseBoolean(getProperty("maximize.window"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout"));
    }

    public static String getScreenshotFolder() {
        return getProperty("screenshot.folder");
    }

    public static String getExtentReportPath() {
        return getProperty("extent.report.path");
    }

    public static String getTestDataPath() {
        return getProperty("testdata.path");
    }
}
