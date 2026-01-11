package com.tealium.ecommerce.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(WaitHelper.class);

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, ConfigReader.getExplicitWait());
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        logger.debug("Waiting for element to be visible");
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        logger.debug("Waiting for element to be clickable");
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean waitForElementToBeInvisible(WebElement element) {
        logger.debug("Waiting for element to be invisible");
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public boolean waitForTitleContains(String title) {
        logger.debug("Waiting for page title to contain: {}", title);
        return wait.until(ExpectedConditions.titleContains(title));
    }

    public boolean waitForUrlContains(String url) {
        logger.debug("Waiting for URL to contain: {}", url);
        return wait.until(ExpectedConditions.urlContains(url));
    }

    public WebElement waitForElementWithCustomTimeout(WebElement element, int timeoutInSeconds) {
        logger.debug("Waiting for element with custom timeout: {} seconds", timeoutInSeconds);
        WebDriverWait customWait = new WebDriverWait(driver, timeoutInSeconds);
        return customWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void hardWait(int milliseconds) {
        try {
            logger.warn("Using hard wait for {} milliseconds", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Hard wait interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void waitForPageLoad() {
        logger.debug("Waiting for page to load");
        wait.until(driver -> ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("return document.readyState").equals("complete"));
    }

    public void waitForCssTransition(WebElement element, String cssProperty, int timeoutMillis) {
        logger.debug("Waiting for CSS transition on property: {}", cssProperty);
        long startTime = System.currentTimeMillis();
        String initialValue = element.getCssValue(cssProperty);

        try {
            wait.until(driver -> {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed >= timeoutMillis) {
                    return true;
                }
                String currentValue = element.getCssValue(cssProperty);
                return !currentValue.equals(initialValue);
            });

            Thread.sleep(100);
        } catch (Exception e) {
            logger.warn("CSS transition wait completed with timeout: {}", e.getMessage());
        }
    }

    public boolean waitForStyleChange(WebElement element, String cssProperty, String initialValue, int timeoutSeconds) {
        logger.debug("Waiting for style change on property: {}", cssProperty);
        try {
            WebDriverWait customWait = new WebDriverWait(driver, timeoutSeconds);
            return customWait.until(driver -> {
                String currentValue = element.getCssValue(cssProperty);
                boolean changed = !currentValue.equals(initialValue);
                if (changed) {
                    logger.debug("Style changed from '{}' to '{}'", initialValue, currentValue);
                }
                return changed;
            });
        } catch (Exception e) {
            logger.warn("Style did not change within timeout: {}", e.getMessage());
            return false;
        }
    }
}
