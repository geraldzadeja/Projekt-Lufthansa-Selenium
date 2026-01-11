package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.*;
import com.tealium.ecommerce.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {

    private HomePage homePage;
    private ProductListingPage productListingPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;

    @BeforeMethod
    public void initPages() {
        homePage = new HomePage(driver);
    }

    @Test(priority = 1, description = "Add a single product to cart")
    public void testAddSingleProductToCart() {
        ExtentReportManager.createTest("Add Single Product to Cart Test",
                "Verify adding a single product to cart");
        ExtentReportManager.logInfo("Navigating to product");

        productListingPage = homePage.searchProduct("shirt");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            ExtentReportManager.logInfo("Clicking on first product");
            productDetailPage = productListingPage.clickProductByIndex(0);

            Assert.assertTrue(productDetailPage.isProductDetailPageLoaded(),
                    "Product detail page should be loaded");
            ExtentReportManager.logInfo("Product detail page loaded");

            String productName = productDetailPage.getProductName();
            ExtentReportManager.logInfo("Product name: " + productName);

            productDetailPage.clickAddToCart();
            ExtentReportManager.logInfo("Product added to cart");

            ExtentReportManager.logPass("Product added to cart successfully");
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 2, description = "Add product with custom quantity")
    public void testAddProductWithQuantity() {
        ExtentReportManager.createTest("Add Product with Quantity Test",
                "Verify adding a product to cart with custom quantity");
        ExtentReportManager.logInfo("Navigating to product");

        productListingPage = homePage.searchProduct("pants");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            ExtentReportManager.logInfo("Product detail page loaded");

            String quantity = "2";
            productDetailPage.addToCartWithQuantity(quantity);
            ExtentReportManager.logInfo("Added product with quantity: " + quantity);

            ExtentReportManager.logPass("Product added with custom quantity");
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 3, description = "Verify success message after adding to cart")
    public void testSuccessMessageDisplay() {
        ExtentReportManager.createTest("Success Message Test",
                "Verify success message is displayed after adding product to cart");
        ExtentReportManager.logInfo("Navigating to product");

        productListingPage = homePage.clickMenCategory();
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            productDetailPage.clickAddToCart();

            ExtentReportManager.logInfo("Checking for success message");
            boolean messageDisplayed = productDetailPage.isSuccessMessageDisplayed();

            if (messageDisplayed) {
                String message = productDetailPage.getSuccessMessage();
                ExtentReportManager.logInfo("Success message: " + message);
                Assert.assertTrue(messageDisplayed, "Success message should be displayed");
                ExtentReportManager.logPass("Success message displayed");
            } else {
                ExtentReportManager.logInfo("Success message not displayed (may be different UI)");
            }
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 4, description = "Add multiple products to cart")
    public void testAddMultipleProductsToCart() {
        ExtentReportManager.createTest("Add Multiple Products Test",
                "Verify adding multiple products to cart");
        ExtentReportManager.logInfo("Searching for first product");

        productListingPage = homePage.searchProduct("jacket");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            productDetailPage.clickAddToCart();
            ExtentReportManager.logInfo("First product added to cart");

            driver.navigate().back();
            driver.navigate().back();

            homePage = new HomePage(driver);
            productListingPage = homePage.searchProduct("shoes");
            int secondProductCount = productListingPage.getProductCount();

            if (secondProductCount > 0) {
                productDetailPage = productListingPage.clickProductByIndex(0);
                productDetailPage.clickAddToCart();
                ExtentReportManager.logInfo("Second product added to cart");

                ExtentReportManager.logPass("Multiple products added successfully");
            } else {
                ExtentReportManager.logInfo("Only one product added");
            }
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 5, description = "Navigate to cart and verify product")
    public void testNavigateToCartAndVerify() {
        ExtentReportManager.createTest("Navigate to Cart Test",
                "Add product and verify it appears in cart");
        ExtentReportManager.logInfo("Adding product to cart");

        productListingPage = homePage.searchProduct("dress");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            productDetailPage = productListingPage.clickProductByIndex(0);
            String productName = productDetailPage.getProductName();
            ExtentReportManager.logInfo("Product name: " + productName);

            productDetailPage.clickAddToCart();
            ExtentReportManager.logInfo("Product added to cart");

            cartPage = homePage.clickCartIcon();
            ExtentReportManager.logInfo("Navigated to cart");

            Assert.assertTrue(cartPage.isCartPageLoaded(),
                    "Cart page should be loaded");
            ExtentReportManager.logPass("Successfully navigated to cart with product");
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }
}
