package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class RegistrationPage extends BasePage {

    @FindBy(xpath = "//a[@title='Register']")
    private WebElement registerLink;

    @FindBy(id = "firstname")
    private WebElement firstNameField;

    @FindBy(id = "lastname")
    private WebElement lastNameField;

    @FindBy(id = "email_address")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "input[name='password_confirmation'], input[id='password-confirmation'], input[id='confirmation']")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//button[@title='Register' or contains(text(),'REGISTER')]")
    private WebElement createAccountButton;

    @FindBy(css = ".message-success, .success-msg, .messages .success, div[data-ui-id*='message-success'], .page.messages .message-success")
    private WebElement successMessage;

    @FindBy(css = ".skip-account .skip-link, a[href*='account'], button.skip-account")
    private WebElement accountMenuToggle;

    @FindBy(xpath = "//a[contains(@href,'account/logout') or contains(text(),'Sign Out') or contains(text(),'Log Out')]")
    private WebElement signOutLink;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void clickRegisterLink() {
        logger.info("Clicking Register link");
        click(registerLink);
    }

    public void fillRegistrationForm(String firstName, String lastName, String email, String password) {
        logger.info("Filling registration form");
        sendKeys(firstNameField, firstName);
        sendKeys(lastNameField, lastName);
        sendKeys(emailField, email);
        sendKeys(passwordField, password);
        sendKeys(confirmPasswordField, password);
    }

    public void submitForm() {
        logger.info("Submitting registration form");
        click(createAccountButton);
        waitHelper.waitForUrlContains("account/index");
    }

    public boolean isSuccessMessageDisplayed() {
        logger.info("Checking if success message is displayed");
        try {
            waitHelper.waitForElementToBeVisible(successMessage);
            return isDisplayed(successMessage);
        } catch (Exception e) {
            logger.info("Success message not found: " + e.getMessage());
            return false;
        }
    }

    public String getSuccessMessage() {
        logger.info("Getting success message text");
        return getText(successMessage);
    }

    public void logout() {
        logger.info("Logging out");
        click(accountMenuToggle);
        click(signOutLink);
    }

    public void debugPageMessages() {
        logger.info("=== Debugging page messages ===");
        try {
            List<WebElement> allMessages = driver.findElements(By.cssSelector("div[class*='message'], div[class*='error'], div[role='alert']"));
            logger.info("Found " + allMessages.size() + " message elements");
            for (int i = 0; i < allMessages.size(); i++) {
                WebElement msg = allMessages.get(i);
                if (msg.isDisplayed()) {
                    logger.info("Message " + i + ": class=" + msg.getAttribute("class") + ", text=" + msg.getText());
                }
            }
        } catch (Exception e) {
            logger.error("Error debugging messages: " + e.getMessage());
        }
    }
}
