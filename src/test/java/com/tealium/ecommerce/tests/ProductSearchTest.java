package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.HomePage;
import com.tealium.ecommerce.pages.ProductListingPage;
import com.tealium.ecommerce.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductSearchTest extends BaseTest {

    private HomePage homePage;
    private ProductListingPage productListingPage;

    @BeforeMethod
    public void initPages() {
        homePage = new HomePage(driver);
    }

    @Test(priority = 1, description = "Search for a product and verify results")
    public void testProductSearch() {
        ExtentReportManager.createTest("Product Search Test",
                "Search for a product and verify search results are displayed");
        ExtentReportManager.logInfo("Starting product search test");

        String searchTerm = "shirt";
        ExtentReportManager.logInfo("Searching for: " + searchTerm);

        productListingPage = homePage.searchProduct(searchTerm);
        ExtentReportManager.logInfo("Search executed");

        Assert.assertTrue(productListingPage.isProductListingPageLoaded(),
                "Product listing page should be loaded");
        ExtentReportManager.logPass("Product search successful");
    }

    @Test(priority = 2, description = "Verify product count after search")
    public void testProductCount() {
        ExtentReportManager.createTest("Product Count Test",
                "Verify that products are displayed after search");
        ExtentReportManager.logInfo("Searching for products");

        productListingPage = homePage.searchProduct("jacket");
        int productCount = productListingPage.getProductCount();

        ExtentReportManager.logInfo("Products found: " + productCount);
        Assert.assertTrue(productCount > 0,
                "At least one product should be displayed");
        ExtentReportManager.logPass("Products displayed successfully");
    }

    @Test(priority = 3, description = "Verify search with no results")
    public void testSearchWithNoResults() {
        ExtentReportManager.createTest("No Results Search Test",
                "Verify behavior when search returns no results");
        ExtentReportManager.logInfo("Searching for non-existent product");

        productListingPage = homePage.searchProduct("xyznonexistentproduct123");
        ExtentReportManager.logInfo("Search executed for invalid product");

        int productCount = productListingPage.getProductCount();
        ExtentReportManager.logInfo("Product count: " + productCount);

        Assert.assertEquals(productCount, 0,
                "No products should be displayed for invalid search");
        ExtentReportManager.logPass("No results search handled correctly");
    }

    @Test(priority = 4, description = "Verify clicking on a product from search results")
    public void testClickProductFromSearch() {
        ExtentReportManager.createTest("Click Product from Search Test",
                "Verify clicking on a product navigates to product detail page");
        ExtentReportManager.logInfo("Searching for products");

        productListingPage = homePage.searchProduct("pants");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            ExtentReportManager.logInfo("Clicking on first product");
            productListingPage.clickProductByIndex(0);

            Assert.assertTrue(driver.getCurrentUrl().contains("product"),
                    "Should navigate to product detail page");
            ExtentReportManager.logPass("Product navigation successful");
        } else {
            ExtentReportManager.logSkip("No products found to click");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 5, description = "Verify product titles are displayed")
    public void testProductTitlesDisplay() {
        ExtentReportManager.createTest("Product Titles Display Test",
                "Verify that product titles are displayed in search results");
        ExtentReportManager.logInfo("Searching for products");

        productListingPage = homePage.searchProduct("shoes");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            String productTitle = productListingPage.getProductTitle(0);
            ExtentReportManager.logInfo("First product title: " + productTitle);

            Assert.assertFalse(productTitle.isEmpty(),
                    "Product title should not be empty");
            ExtentReportManager.logPass("Product titles displayed correctly");
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }

    @Test(priority = 6, description = "Verify product prices are displayed")
    public void testProductPricesDisplay() {
        ExtentReportManager.createTest("Product Prices Display Test",
                "Verify that product prices are displayed in search results");
        ExtentReportManager.logInfo("Searching for products");

        productListingPage = homePage.searchProduct("dress");
        int productCount = productListingPage.getProductCount();

        if (productCount > 0) {
            String productPrice = productListingPage.getProductPrice(0);
            ExtentReportManager.logInfo("First product price: " + productPrice);

            Assert.assertFalse(productPrice.isEmpty(),
                    "Product price should not be empty");
            ExtentReportManager.logPass("Product prices displayed correctly");
        } else {
            ExtentReportManager.logSkip("No products found");
            Assert.fail("No products available for testing");
        }
    }
}
