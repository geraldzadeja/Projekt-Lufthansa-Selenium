package com.tealium.ecommerce.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            logger.warn("Retrying test '{}' - Attempt {}/{}",
                result.getName(), retryCount, MAX_RETRY_COUNT);
            logger.warn("Failure reason: {}", result.getThrowable().getMessage());
            return true;
        }
        logger.error("Test '{}' failed after {} retries", result.getName(), MAX_RETRY_COUNT);
        return false;
    }
}
