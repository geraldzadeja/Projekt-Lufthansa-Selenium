package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {

    @FindBy(css = ".product-name")
    private WebElement productName;

    @FindBy(css = ".product-price")
    private WebElement productPrice;

    @FindBy(css = ".product-description")
    private WebElement productDescription;

    @FindBy(css = "button.add-to-cart")
    private WebElement addToCartButton;

    @FindBy(css = "input.quantity")
    private WebElement quantityInput;

    @FindBy(css = "button.increase-quantity")
    private WebElement increaseQuantityButton;

    @FindBy(css = "button.decrease-quantity")
    private WebElement decreaseQuantityButton;

    @FindBy(css = ".size-selector")
    private WebElement sizeSelector;

    @FindBy(css = ".color-selector")
    private WebElement colorSelector;

    @FindBy(css = ".product-image")
    private WebElement productImage;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    @FindBy(css = "button.view-cart")
    private WebElement viewCartButton;

    @FindBy(css = ".availability")
    private WebElement availabilityStatus;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductDetailPageLoaded() {
        logger.info("Verifying product detail page is loaded");
        return isDisplayed(productName) && isDisplayed(addToCartButton);
    }

    public String getProductName() {
        String name = getText(productName);
        logger.info("Product name: {}", name);
        return name;
    }

    public String getProductPrice() {
        String price = getText(productPrice);
        logger.info("Product price: {}", price);
        return price;
    }

    public String getProductDescription() {
        String description = getText(productDescription);
        logger.info("Product description: {}", description);
        return description;
    }

    public void setQuantity(String quantity) {
        logger.info("Setting product quantity to: {}", quantity);
        sendKeys(quantityInput, quantity);
    }

    public void increaseQuantity() {
        logger.info("Increasing product quantity");
        click(increaseQuantityButton);
    }

    public void decreaseQuantity() {
        logger.info("Decreasing product quantity");
        click(decreaseQuantityButton);
    }

    public String getQuantity() {
        String quantity = quantityInput.getAttribute("value");
        logger.info("Current quantity: {}", quantity);
        return quantity;
    }

    public void addToCart() {
        logger.info("Adding product to cart");
        clickAddToCart();
    }

    public void clickAddToCart() {
        logger.info("Clicking add to cart button");
        click(addToCartButton);
    }

    public void addToCartWithQuantity(String quantity) {
        logger.info("Adding product to cart with quantity: {}", quantity);
        setQuantity(quantity);
        clickAddToCart();
    }

    public boolean isSuccessMessageDisplayed() {
        logger.info("Verifying success message is displayed");
        return isDisplayed(successMessage);
    }

    public String getSuccessMessage() {
        String message = getText(successMessage);
        logger.info("Success message: {}", message);
        return message;
    }

    public CartPage clickViewCart() {
        logger.info("Clicking view cart button");
        click(viewCartButton);
        return new CartPage(driver);
    }

    public boolean isProductInStock() {
        String status = getText(availabilityStatus);
        logger.info("Product availability status: {}", status);
        return status.toLowerCase().contains("in stock");
    }

    public boolean isProductImageDisplayed() {
        logger.info("Verifying product image is displayed");
        return isDisplayed(productImage);
    }
}
