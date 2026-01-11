package com.tealium.ecommerce.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.function.Function;

public class ElementHelper {

    private static final Logger logger = LogManager.getLogger(ElementHelper.class);
    private static final int MAX_RETRIES = 3;

    public static <T> T retryOnStale(Function<WebElement, T> action, WebElement element) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                return action.apply(element);
            } catch (StaleElementReferenceException e) {
                attempts++;
                logger.warn("StaleElementReferenceException caught, retry attempt {}/{}", attempts, MAX_RETRIES);
                if (attempts >= MAX_RETRIES) {
                    logger.error("Max retries reached for stale element");
                    throw e;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    logger.error("Retry sleep interrupted: {}", ie.getMessage());
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }
            }
        }
        throw new RuntimeException("Failed to execute action after retries");
    }

    public static void clickWithRetry(WebElement element) {
        retryOnStale(e -> {
            e.click();
            logger.debug("Element clicked successfully");
            return null;
        }, element);
    }

    public static String getTextWithRetry(WebElement element) {
        return retryOnStale(WebElement::getText, element);
    }

    public static void sendKeysWithRetry(WebElement element, String text) {
        retryOnStale(e -> {
            e.clear();
            e.sendKeys(text);
            logger.debug("Text entered successfully: {}", text);
            return null;
        }, element);
    }

    public static boolean isDisplayedWithRetry(WebElement element) {
        try {
            return retryOnStale(WebElement::isDisplayed, element);
        } catch (Exception e) {
            logger.warn("Element not displayed or not found: {}", e.getMessage());
            return false;
        }
    }
}
