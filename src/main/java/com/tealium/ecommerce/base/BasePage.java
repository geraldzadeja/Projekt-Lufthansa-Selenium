package com.tealium.ecommerce.base;

import com.tealium.ecommerce.utils.WaitHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    protected WebDriver driver;
    protected WaitHelper waitHelper;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        return title;
    }

    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }

    protected void click(WebElement element) {
        waitHelper.waitForElementToBeClickable(element);
        element.click();
        logger.info("Clicked on element: {}", element);
    }

    protected void clickWithJS(WebElement element) {
        waitHelper.waitForElementToBeClickable(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        logger.info("Clicked on element using JavaScript: {}", element);
    }

    protected void sendKeys(WebElement element, String text) {
        waitHelper.waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
        logger.info("Entered text '{}' into element: {}", text, element);
    }

    protected String getText(WebElement element) {
        waitHelper.waitForElementToBeVisible(element);
        String text = element.getText();
        logger.info("Retrieved text '{}' from element: {}", text, element);
        return text;
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            waitHelper.waitForElementToBeVisible(element);
            boolean displayed = element.isDisplayed();
            logger.info("Element displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            logger.error("Element not displayed: {}", e.getMessage());
            return false;
        }
    }

    protected boolean isEnabled(WebElement element) {
        boolean enabled = element.isEnabled();
        logger.info("Element enabled: {}", enabled);
        return enabled;
    }

    public void navigateTo(String url) {
        driver.get(url);
        logger.info("Navigated to URL: {}", url);
    }

    public void refreshPage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
    }

    public void navigateBack() {
        driver.navigate().back();
        logger.info("Navigated back");
    }

    public void navigateForward() {
        driver.navigate().forward();
        logger.info("Navigated forward");
    }

    public void waitForUrlContains(String urlPart) {
        waitHelper.waitForUrlContains(urlPart);
        logger.info("URL now contains: {}", urlPart);
    }

    public void waitForTitleContains(String titlePart) {
        waitHelper.waitForTitleContains(titlePart);
        logger.info("Title now contains: {}", titlePart);
    }
}
