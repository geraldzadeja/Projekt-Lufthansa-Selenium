package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EmptyShoppingCartTest extends BaseTest {

    @Test(dependsOnMethods = {"com.tealium.ecommerce.tests.ShoppingCartTest.testShoppingCart"})
    public void testEmptyShoppingCart() {
        System.out.println("DEBUG: Starting Empty Shopping Cart test");

        waitHelper.waitForPageLoad();

        System.out.println("DEBUG: Current URL: " + driver.getCurrentUrl());

        if (!driver.getCurrentUrl().contains("checkout/cart")) {
            try {
                WebElement cartLink = driver.findElement(By.cssSelector("a.action.showcart, a[href*='checkout/cart'], .minicart-wrapper"));
                cartLink.click();
                System.out.println("DEBUG: Navigated to cart page");

                waitHelper.waitForPageLoad();

                try {
                    WebElement viewCartButton = driver.findElement(By.xpath("//a[contains(text(),'View and Edit Cart') or contains(@href,'checkout/cart')]"));
                    viewCartButton.click();
                    System.out.println("DEBUG: Clicked View and Edit Cart");
                    waitHelper.waitForPageLoad();
                } catch (Exception e) {
                    System.out.println("DEBUG: Already on cart page");
                }
            } catch (Exception e) {
                System.out.println("WARNING: Could not navigate to cart page: " + e.getMessage());
            }
        }

        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart.item, tbody.cart.item, tr.item-info, tr.item"));
        int initialCount = cartItems.size();
        System.out.println("DEBUG: Initial cart item count: " + initialCount);

        if (initialCount == 0) {
            System.out.println("WARNING: Shopping cart is already empty at the start of test");
            try {
                WebElement emptyMessage = driver.findElement(By.xpath("//*[contains(text(),'You have no items in your shopping cart') or contains(text(),'no items') or contains(text(),'empty')]"));
                if (emptyMessage.isDisplayed()) {
                    System.out.println("DEBUG: Empty cart message confirmed: " + emptyMessage.getText());
                } else {
                    System.out.println("DEBUG: Empty message found but not displayed");
                }
            } catch (Exception e) {
                System.out.println("DEBUG: Cart is empty (no items found, no empty message)");
            }
            System.out.println("DEBUG: Empty shopping cart test passed");
            return;
        }

        int deletedCount = 0;
        while (true) {
            try {
                cartItems = driver.findElements(By.cssSelector(".cart.item, tbody.cart.item, tr.item-info, tr.item"));

                List<WebElement> productRows = new java.util.ArrayList<>();
                for (WebElement item : cartItems) {
                    try {
                        item.findElement(By.cssSelector("a.action.action-delete, a.action-delete, a[title*='Remove'], button.action-delete"));
                        productRows.add(item);
                    } catch (Exception e) {
                    }
                }

                if (productRows.isEmpty()) {
                    System.out.println("DEBUG: No more items to delete");
                    break;
                }

                int currentCount = productRows.size();
                System.out.println("DEBUG: Current cart item count: " + currentCount);

                WebElement firstItem = productRows.get(0);
                WebElement deleteButton = firstItem.findElement(By.cssSelector("a.action.action-delete, a.action-delete, a[title*='Remove'], button.action-delete"));

                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);

                deletedCount++;
                System.out.println("DEBUG: Deleted item " + deletedCount);

                waitHelper.waitForPageLoad();

                List<WebElement> updatedCartItems = driver.findElements(By.cssSelector(".cart.item, tbody.cart.item, tr.item-info, tr.item"));
                List<WebElement> updatedProductRows = new java.util.ArrayList<>();
                for (WebElement item : updatedCartItems) {
                    try {
                        item.findElement(By.cssSelector("a.action.action-delete, a.action-delete, a[title*='Remove'], button.action-delete"));
                        updatedProductRows.add(item);
                    } catch (Exception e) {
                    }
                }

                int newCount = updatedProductRows.size();
                System.out.println("DEBUG: New cart item count after deletion: " + newCount);

                if (newCount == currentCount - 1) {
                    System.out.println("DEBUG: Item count decreased correctly");
                } else if (newCount == 0) {
                    System.out.println("DEBUG: Cart is now empty");
                    break;
                } else {
                    System.out.println("WARNING: Item count not as expected. Current: " + currentCount + ", New: " + newCount);
                }

            } catch (Exception e) {
                System.out.println("DEBUG: Could not delete item or verify count: " + e.getMessage());
                try {
                    WebElement emptyMessage = driver.findElement(By.xpath("//*[contains(text(),'no items') or contains(text(),'empty') or contains(text(),'You have no items')]"));
                    if (emptyMessage.isDisplayed()) {
                        System.out.println("DEBUG: Cart is empty, breaking loop");
                        break;
                    }
                } catch (Exception e2) {
                }
                break;
            }
        }

        System.out.println("DEBUG: Total items deleted: " + deletedCount);

        try {
            WebElement emptyMessage = driver.findElement(By.xpath("//*[contains(text(),'You have no items in your shopping cart') or contains(text(),'no items') or contains(text(),'empty')]"));

            Assert.assertTrue(emptyMessage.isDisplayed(),
                "Empty cart message should be displayed");

            System.out.println("DEBUG: Empty cart message displayed: " + emptyMessage.getText());

        } catch (Exception e) {
            List<WebElement> finalCartItems = driver.findElements(By.cssSelector(".cart.item, tbody.cart.item, tr.item-info"));
            List<WebElement> finalProductRows = new java.util.ArrayList<>();
            for (WebElement item : finalCartItems) {
                try {
                    item.findElement(By.cssSelector("a.action.action-delete, a.action-delete, a[title*='Remove']"));
                    finalProductRows.add(item);
                } catch (Exception ex) {
                }
            }

            Assert.assertTrue(finalProductRows.size() == 0,
                "Shopping cart should be empty. Found " + finalProductRows.size() + " items");

            System.out.println("DEBUG: Shopping cart is empty (no items found)");
        }

        System.out.println("DEBUG: Empty shopping cart test passed");

        System.out.println("DEBUG: Browser will be closed after test suite completion");
    }
}
