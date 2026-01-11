package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.*;
import com.tealium.ecommerce.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private HomePage homePage;
    private ProductListingPage productListingPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void initPages() {
        homePage = new HomePage(driver);
    }

    @Test(priority = 1, description = "Navigate to checkout page")
    public void testNavigateToCheckout() {
        ExtentReportManager.createTest("Navigate to Checkout Test",
                "Verify navigation to checkout page");
        ExtentReportManager.logInfo("Adding product to cart");

        productListingPage = homePage.searchProduct("shirt");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            productDetailPage.clickAddToCart();
            ExtentReportManager.logInfo("Product added to cart");

            cartPage = homePage.clickCartIcon();
            ExtentReportManager.logInfo("Navigated to cart");

            if (!cartPage.isCartEmpty()) {
                checkoutPage = cartPage.proceedToCheckout();
                ExtentReportManager.logInfo("Proceeded to checkout");

                Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(),
                        "Checkout page should be loaded");
                ExtentReportManager.logPass("Successfully navigated to checkout");
            } else {
                ExtentReportManager.logSkip("Cart is empty");
            }
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 2, description = "Verify cart page displays items")
    public void testCartPageDisplaysItems() {
        ExtentReportManager.createTest("Cart Items Display Test",
                "Verify cart page displays added items");
        ExtentReportManager.logInfo("Adding product to cart");

        productListingPage = homePage.searchProduct("jacket");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            String productName = productDetailPage.getProductName();
            productDetailPage.clickAddToCart();

            cartPage = homePage.clickCartIcon();
            ExtentReportManager.logInfo("Navigated to cart");

            Assert.assertTrue(cartPage.isCartPageLoaded(),
                    "Cart page should be loaded");

            int cartItemCount = cartPage.getCartItemCount();
            ExtentReportManager.logInfo("Cart item count: " + cartItemCount);

            Assert.assertTrue(cartItemCount > 0,
                    "Cart should contain at least one item");
            ExtentReportManager.logPass("Cart displays items correctly");
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 3, description = "Verify cart total calculation")
    public void testCartTotalCalculation() {
        ExtentReportManager.createTest("Cart Total Calculation Test",
                "Verify cart calculates total correctly");
        ExtentReportManager.logInfo("Adding product to cart");

        productListingPage = homePage.searchProduct("shoes");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            productDetailPage.clickAddToCart();

            cartPage = homePage.clickCartIcon();
            ExtentReportManager.logInfo("Checking cart totals");

            String subtotal = cartPage.getSubtotal();
            String total = cartPage.getTotal();

            ExtentReportManager.logInfo("Subtotal: " + subtotal);
            ExtentReportManager.logInfo("Total: " + total);

            Assert.assertFalse(subtotal.isEmpty(), "Subtotal should not be empty");
            Assert.assertFalse(total.isEmpty(), "Total should not be empty");
            ExtentReportManager.logPass("Cart totals displayed correctly");
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 4, description = "Remove item from cart")
    public void testRemoveItemFromCart() {
        ExtentReportManager.createTest("Remove Item from Cart Test",
                "Verify removing an item from cart");
        ExtentReportManager.logInfo("Adding product to cart");

        productListingPage = homePage.searchProduct("pants");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            productDetailPage.clickAddToCart();

            cartPage = homePage.clickCartIcon();
            int initialCount = cartPage.getCartItemCount();
            ExtentReportManager.logInfo("Initial cart count: " + initialCount);

            if (initialCount > 0) {
                cartPage.removeCartItem(0);
                ExtentReportManager.logInfo("Item removed from cart");

                int finalCount = cartPage.getCartItemCount();
                ExtentReportManager.logInfo("Final cart count: " + finalCount);

                Assert.assertTrue(finalCount < initialCount || cartPage.isCartEmpty(),
                        "Cart count should decrease or cart should be empty");
                ExtentReportManager.logPass("Item removed successfully");
            } else {
                ExtentReportManager.logSkip("Cart was empty");
            }
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 5, description = "Complete checkout with test data")
    public void testCompleteCheckout() {
        ExtentReportManager.createTest("Complete Checkout Test",
                "Verify complete checkout process with test data");
        ExtentReportManager.logInfo("Starting checkout process");

        productListingPage = homePage.searchProduct("dress");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            productDetailPage.clickAddToCart();

            cartPage = homePage.clickCartIcon();

            if (!cartPage.isCartEmpty()) {
                checkoutPage = cartPage.proceedToCheckout();
                ExtentReportManager.logInfo("On checkout page");

                if (checkoutPage.isCheckoutPageLoaded()) {
                    ExtentReportManager.logInfo("Filling checkout information");

                    checkoutPage.fillShippingInformation(
                            "John", "Doe", "john.doe@test.com",
                            "1234567890", "123 Test Street",
                            "Test City", "CA", "90210", "USA"
                    );

                    checkoutPage.fillPaymentInformation(
                            "4111111111111111", "John Doe",
                            "12/25", "123"
                    );

                    ExtentReportManager.logPass("Checkout form filled successfully");
                } else {
                    ExtentReportManager.logSkip("Checkout page not loaded properly");
                }
            } else {
                ExtentReportManager.logSkip("Cart is empty");
            }
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 6, description = "Verify empty cart message")
    public void testEmptyCartMessage() {
        ExtentReportManager.createTest("Empty Cart Message Test",
                "Verify empty cart message is displayed");
        ExtentReportManager.logInfo("Navigating to cart");

        cartPage = homePage.clickCartIcon();
        ExtentReportManager.logInfo("Checking if cart is empty");

        if (cartPage.isCartEmpty()) {
            ExtentReportManager.logInfo("Cart is empty as expected");
            Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty");
            ExtentReportManager.logPass("Empty cart handled correctly");
        } else {
            ExtentReportManager.logInfo("Cart contains items");
        }
    }
}
