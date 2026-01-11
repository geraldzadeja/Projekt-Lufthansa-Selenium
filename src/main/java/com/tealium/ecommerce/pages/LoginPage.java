package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//a[@title='Sign In' or @title='Log In' or contains(@href,'customer/account/login')]")
    private WebElement signInLink;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "pass")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@name='send' or @title='Login' or contains(@class,'action login')]")
    private WebElement signInButton;

    @FindBy(css = ".welcome-msg, .logged-in, span.customer-name, .skip-account")
    private WebElement welcomeMessage;

    @FindBy(css = ".skip-account .skip-link, a[href*='account'], button.skip-account")
    private WebElement accountMenuToggle;

    @FindBy(xpath = "//a[contains(@href,'account/logout') or contains(text(),'Sign Out') or contains(text(),'Log Out')]")
    private WebElement signOutLink;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickSignInLink() {
        logger.info("Clicking Sign In link");
        click(signInLink);
    }

    public void login(String email, String password) {
        logger.info("Logging in with email: {}", email);
        waitForUrlContains("account/login");
        sendKeys(emailField, email);
        sendKeys(passwordField, password);
        clickWithJS(signInButton);
        waitHelper.waitForUrlContains("account");
    }

    public boolean isUsernameDisplayed() {
        logger.info("Checking if username is displayed");
        try {
            waitHelper.waitForElementToBeVisible(welcomeMessage);
            return isDisplayed(welcomeMessage);
        } catch (Exception e) {
            logger.error("Username not displayed: {}", e.getMessage());
            return false;
        }
    }

    public String getWelcomeMessageText() {
        logger.info("Getting welcome message text");
        return getText(welcomeMessage);
    }

    public void logout() {
        logger.info("Logging out");
        click(accountMenuToggle);
        waitHelper.waitForElementToBeClickable(signOutLink);
        click(signOutLink);
    }
}
