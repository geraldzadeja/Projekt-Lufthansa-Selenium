package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".cart-item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart-item-name")
    private List<WebElement> cartItemNames;

    @FindBy(css = ".cart-item-price")
    private List<WebElement> cartItemPrices;

    @FindBy(css = ".cart-item-quantity")
    private List<WebElement> cartItemQuantities;

    @FindBy(css = "button.remove-item")
    private List<WebElement> removeButtons;

    @FindBy(css = ".subtotal")
    private WebElement subtotal;

    @FindBy(css = ".tax")
    private WebElement tax;

    @FindBy(css = ".total")
    private WebElement total;

    @FindBy(css = "button.proceed-to-checkout")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = "button.continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(css = ".empty-cart-message")
    private WebElement emptyCartMessage;

    @FindBy(css = "button.update-cart")
    private WebElement updateCartButton;

    @FindBy(css = "input.coupon-code")
    private WebElement couponCodeInput;

    @FindBy(css = "button.apply-coupon")
    private WebElement applyCouponButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageLoaded() {
        logger.info("Verifying cart page is loaded");
        return getCurrentUrl().contains("cart") || isDisplayed(proceedToCheckoutButton);
    }

    public int getCartItemCount() {
        int count = cartItems.size();
        logger.info("Cart item count: {}", count);
        return count;
    }

    public boolean isCartEmpty() {
        logger.info("Checking if cart is empty");
        return cartItems.isEmpty() || isDisplayed(emptyCartMessage);
    }

    public String getCartItemName(int index) {
        String name = getText(cartItemNames.get(index));
        logger.info("Cart item name at index {}: {}", index, name);
        return name;
    }

    public String getCartItemPrice(int index) {
        String price = getText(cartItemPrices.get(index));
        logger.info("Cart item price at index {}: {}", index, price);
        return price;
    }

    public String getCartItemQuantity(int index) {
        String quantity = cartItemQuantities.get(index).getAttribute("value");
        logger.info("Cart item quantity at index {}: {}", index, quantity);
        return quantity;
    }

    public void removeCartItem(int index) {
        logger.info("Removing cart item at index: {}", index);
        click(removeButtons.get(index));
    }

    public String getSubtotal() {
        String amount = getText(subtotal);
        logger.info("Subtotal: {}", amount);
        return amount;
    }

    public String getTax() {
        String amount = getText(tax);
        logger.info("Tax: {}", amount);
        return amount;
    }

    public String getTotal() {
        String amount = getText(total);
        logger.info("Total: {}", amount);
        return amount;
    }

    public CheckoutPage proceedToCheckout() {
        logger.info("Proceeding to checkout");
        click(proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }

    public HomePage continueShopping() {
        logger.info("Continuing shopping");
        click(continueShoppingButton);
        return new HomePage(driver);
    }

    public void updateCartQuantity(int index, String quantity) {
        logger.info("Updating cart item {} quantity to: {}", index, quantity);
        sendKeys(cartItemQuantities.get(index), quantity);
        click(updateCartButton);
    }

    public void applyCoupon(String couponCode) {
        logger.info("Applying coupon code: {}", couponCode);
        sendKeys(couponCodeInput, couponCode);
        click(applyCouponButton);
    }

    public boolean isItemInCart(String productName) {
        logger.info("Checking if item exists in cart: {}", productName);
        for (WebElement itemName : cartItemNames) {
            if (getText(itemName).contains(productName)) {
                logger.info("Item found in cart: {}", productName);
                return true;
            }
        }
        logger.info("Item not found in cart: {}", productName);
        return false;
    }
}
