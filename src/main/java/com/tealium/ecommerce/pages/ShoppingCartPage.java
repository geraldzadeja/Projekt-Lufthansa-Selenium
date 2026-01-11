package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ShoppingCartPage extends BasePage {

    @FindBy(css = ".cart-item")
    private List<WebElement> cartItems;

    @FindBy(css = ".action.delete")
    private List<WebElement> removeButtons;

    @FindBy(css = ".cart-empty")
    private WebElement emptyCartMessage;

    @FindBy(css = "a.action.viewcart")
    private WebElement viewCartButton;

    @FindBy(css = ".counter.qty")
    private WebElement cartCountBadge;

    @FindBy(css = ".action.showcart")
    private WebElement cartIcon;

    @FindBy(css = ".subtitle.empty")
    private WebElement emptyCartText;

    private org.openqa.selenium.support.ui.WebDriverWait wait;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10);
    }

    public void goToCart() {
        logger.info("Navigating to cart page");
        click(cartIcon);
        waitHelper.waitForElementToBeVisible(viewCartButton);
        click(viewCartButton);
    }

    public int getCartItemCount() {
        logger.info("Getting cart item count");
        try {
            waitHelper.waitForElementToBeVisible(cartItems.get(0));
            int count = cartItems.size();
            logger.info("Cart has {} items", count);
            return count;
        } catch (Exception e) {
            logger.info("Cart is empty or items not found");
            return 0;
        }
    }

    public void removeItemByIndex(int index) {
        logger.info("Removing item at index {}", index);
        if (index >= 0 && index < removeButtons.size()) {
            int initialCount = getCartItemCount();
            click(removeButtons.get(index));
            waitHelper.waitForPageLoad();

            wait.until(driver -> getCartItemCount() < initialCount || isEmptyCartMessageDisplayed());
            logger.info("Item removed successfully");
        } else {
            logger.error("Invalid index: {}. Available items: {}", index, removeButtons.size());
        }
    }

    public void removeFirstItem() {
        logger.info("Removing first item from cart");
        removeItemByIndex(0);
    }

    public void removeAllItems() {
        logger.info("Removing all items from cart");
        int itemCount = getCartItemCount();

        while (itemCount > 0) {
            removeFirstItem();
            itemCount = getCartItemCount();
            logger.info("Remaining items: {}", itemCount);
        }

        logger.info("All items removed from cart");
    }

    public boolean isEmptyCartMessageDisplayed() {
        logger.info("Checking if empty cart message is displayed");
        try {
            boolean emptyMessage = isDisplayed(emptyCartMessage);
            if (emptyMessage) {
                logger.info("Empty cart message is displayed");
                return true;
            }
        } catch (Exception e) {
            logger.debug("Primary empty cart message not found, checking alternative");
        }

        try {
            boolean emptyText = isDisplayed(emptyCartText);
            if (emptyText) {
                logger.info("Empty cart text is displayed");
                return true;
            }
        } catch (Exception e) {
            logger.debug("Alternative empty cart text not found");
        }

        logger.info("No empty cart message displayed");
        return false;
    }

    public String getEmptyCartMessage() {
        logger.info("Getting empty cart message text");
        try {
            if (isDisplayed(emptyCartMessage)) {
                return getText(emptyCartMessage);
            }
        } catch (Exception e) {
            logger.debug("Primary empty cart message not accessible");
        }

        try {
            if (isDisplayed(emptyCartText)) {
                return getText(emptyCartText);
            }
        } catch (Exception e) {
            logger.debug("Alternative empty cart text not accessible");
        }

        return "";
    }

    public boolean isCartEmpty() {
        logger.info("Checking if cart is empty");
        return getCartItemCount() == 0 && isEmptyCartMessageDisplayed();
    }
}
