package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    @FindBy(css = "input[name='firstName']")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='lastName']")
    private WebElement lastNameInput;

    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='phone']")
    private WebElement phoneInput;

    @FindBy(css = "input[name='address']")
    private WebElement addressInput;

    @FindBy(css = "input[name='city']")
    private WebElement cityInput;

    @FindBy(css = "select[name='state']")
    private WebElement stateDropdown;

    @FindBy(css = "input[name='zipCode']")
    private WebElement zipCodeInput;

    @FindBy(css = "input[name='country']")
    private WebElement countryInput;

    @FindBy(css = "input[name='cardNumber']")
    private WebElement cardNumberInput;

    @FindBy(css = "input[name='cardName']")
    private WebElement cardNameInput;

    @FindBy(css = "input[name='expiryDate']")
    private WebElement expiryDateInput;

    @FindBy(css = "input[name='cvv']")
    private WebElement cvvInput;

    @FindBy(css = "button.place-order")
    private WebElement placeOrderButton;

    @FindBy(css = ".order-summary")
    private WebElement orderSummary;

    @FindBy(css = ".order-total")
    private WebElement orderTotal;

    @FindBy(css = ".confirmation-message")
    private WebElement confirmationMessage;

    @FindBy(css = ".order-number")
    private WebElement orderNumber;

    @FindBy(css = "input[type='checkbox'][name='terms']")
    private WebElement termsCheckbox;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutPageLoaded() {
        logger.info("Verifying checkout page is loaded");
        return isDisplayed(firstNameInput) && isDisplayed(placeOrderButton);
    }

    public void fillShippingInformation(String firstName, String lastName, String email,
                                       String phone, String address, String city,
                                       String state, String zipCode, String country) {
        logger.info("Filling shipping information");
        sendKeys(firstNameInput, firstName);
        sendKeys(lastNameInput, lastName);
        sendKeys(emailInput, email);
        sendKeys(phoneInput, phone);
        sendKeys(addressInput, address);
        sendKeys(cityInput, city);
        sendKeys(stateDropdown, state);
        sendKeys(zipCodeInput, zipCode);
        sendKeys(countryInput, country);
        logger.info("Shipping information filled successfully");
    }

    public void fillPaymentInformation(String cardNumber, String cardName,
                                      String expiryDate, String cvv) {
        logger.info("Filling payment information");
        sendKeys(cardNumberInput, cardNumber);
        sendKeys(cardNameInput, cardName);
        sendKeys(expiryDateInput, expiryDate);
        sendKeys(cvvInput, cvv);
        logger.info("Payment information filled successfully");
    }

    public void acceptTerms() {
        logger.info("Accepting terms and conditions");
        if (!termsCheckbox.isSelected()) {
            click(termsCheckbox);
        }
    }

    public void clickPlaceOrder() {
        logger.info("Clicking place order button");
        click(placeOrderButton);
    }

    public void completeCheckout(String firstName, String lastName, String email,
                                 String phone, String address, String city,
                                 String state, String zipCode, String country,
                                 String cardNumber, String cardName,
                                 String expiryDate, String cvv) {
        logger.info("Completing checkout process");
        fillShippingInformation(firstName, lastName, email, phone, address,
                              city, state, zipCode, country);
        fillPaymentInformation(cardNumber, cardName, expiryDate, cvv);
        acceptTerms();
        clickPlaceOrder();
        logger.info("Checkout completed");
    }

    public String getOrderSummary() {
        String summary = getText(orderSummary);
        logger.info("Order summary: {}", summary);
        return summary;
    }

    public String getOrderTotal() {
        String total = getText(orderTotal);
        logger.info("Order total: {}", total);
        return total;
    }

    public boolean isOrderConfirmationDisplayed() {
        logger.info("Verifying order confirmation is displayed");
        return isDisplayed(confirmationMessage);
    }

    public String getConfirmationMessage() {
        String message = getText(confirmationMessage);
        logger.info("Confirmation message: {}", message);
        return message;
    }

    public String getOrderNumber() {
        String number = getText(orderNumber);
        logger.info("Order number: {}", number);
        return number;
    }
}
