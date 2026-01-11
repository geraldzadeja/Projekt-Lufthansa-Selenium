package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.HomePage;
import com.tealium.ecommerce.pages.ProductDetailPage;
import com.tealium.ecommerce.pages.ProductListingPage;
import com.tealium.ecommerce.pages.ShoppingCartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmptyCartTest extends BaseTest {

    @Test
    public void testEmptyShoppingCart() {
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage;
        ProductDetailPage productDetailPage;
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        productListingPage = homePage.clickWomenCategory();
        productDetailPage = productListingPage.clickFirstProduct();
        productDetailPage.addToCart();

        homePage = new HomePage(driver);
        productListingPage = homePage.clickWomenCategory();
        productDetailPage = productListingPage.clickProductByIndex(1);
        productDetailPage.addToCart();

        homePage = new HomePage(driver);
        productListingPage = homePage.clickWomenCategory();
        productDetailPage = productListingPage.clickProductByIndex(2);
        productDetailPage.addToCart();

        cartPage.goToCart();

        int initialCartCount = cartPage.getCartItemCount();
        Assert.assertTrue(initialCartCount > 0,
            "Cart should have items before removal. Found: " + initialCartCount);

        int currentCount = initialCartCount;

        while (currentCount > 0) {
            int previousCount = currentCount;
            cartPage.removeFirstItem();
            currentCount = cartPage.getCartItemCount();

            Assert.assertTrue(currentCount < previousCount,
                "Cart item count should decrease after removal. Previous: " + previousCount +
                ", Current: " + currentCount);

            if (currentCount > 0) {
                Assert.assertFalse(cartPage.isEmptyCartMessageDisplayed(),
                    "Empty cart message should not be displayed when items remain");
            }
        }

        Assert.assertEquals(cartPage.getCartItemCount(), 0,
            "Cart should have 0 items after removing all items");

        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed(),
            "Empty cart message should be displayed when cart is empty");

        String emptyMessage = cartPage.getEmptyCartMessage();
        Assert.assertFalse(emptyMessage.isEmpty(),
            "Empty cart message text should not be empty");

        Assert.assertTrue(cartPage.isCartEmpty(),
            "Cart should be completely empty");
    }

    @Test
    public void testRemoveAllItemsAtOnce() {
        HomePage homePage = new HomePage(driver);
        ProductListingPage productListingPage;
        ProductDetailPage productDetailPage;
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        productListingPage = homePage.clickWomenCategory();
        productDetailPage = productListingPage.clickFirstProduct();
        productDetailPage.addToCart();

        homePage = new HomePage(driver);
        productListingPage = homePage.clickWomenCategory();
        productDetailPage = productListingPage.clickProductByIndex(1);
        productDetailPage.addToCart();

        cartPage.goToCart();

        int initialCount = cartPage.getCartItemCount();
        Assert.assertTrue(initialCount > 0, "Cart should have items");

        cartPage.removeAllItems();

        Assert.assertEquals(cartPage.getCartItemCount(), 0,
            "All items should be removed from cart");

        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed(),
            "Empty cart message should be displayed");
    }
}
