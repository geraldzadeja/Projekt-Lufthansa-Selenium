package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.HomePage;
import com.tealium.ecommerce.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void initPage() {
        homePage = new HomePage(driver);
    }

    @Test(priority = 1, description = "Verify home page loads successfully")
    public void testHomePageLoad() {
        ExtentReportManager.createTest("Home Page Load Test",
                "Verify that home page loads successfully");
        ExtentReportManager.logInfo("Verifying home page is loaded");

        Assert.assertTrue(homePage.isHomePageLoaded(),
                "Home page should be loaded");
        ExtentReportManager.logPass("Home page loaded successfully");
    }

    @Test(priority = 2, description = "Verify logo is displayed on home page")
    public void testLogoDisplay() {
        ExtentReportManager.createTest("Logo Display Test",
                "Verify that logo is displayed on home page");
        ExtentReportManager.logInfo("Checking if logo is displayed");

        Assert.assertTrue(homePage.isLogoDisplayed(),
                "Logo should be displayed on home page");
        ExtentReportManager.logPass("Logo is displayed correctly");
    }

    @Test(priority = 3, description = "Verify page title")
    public void testPageTitle() {
        ExtentReportManager.createTest("Page Title Test",
                "Verify home page title");
        ExtentReportManager.logInfo("Getting page title");

        String actualTitle = homePage.getPageTitle();
        ExtentReportManager.logInfo("Page title: " + actualTitle);

        Assert.assertFalse(actualTitle.isEmpty(),
                "Page title should not be empty");
        ExtentReportManager.logPass("Page title verified successfully");
    }

    @Test(priority = 4, description = "Verify navigation to Men category")
    public void testMenCategoryNavigation() {
        ExtentReportManager.createTest("Men Category Navigation Test",
                "Verify navigation to Men category");
        ExtentReportManager.logInfo("Clicking on Men category");

        homePage.clickMenCategory();
        ExtentReportManager.logInfo("Navigated to Men category");

        Assert.assertTrue(driver.getCurrentUrl().contains("men") ||
                        driver.getCurrentUrl().contains("category"),
                "URL should contain men or category");
        ExtentReportManager.logPass("Men category navigation successful");
    }

    @Test(priority = 5, description = "Verify navigation to Women category")
    public void testWomenCategoryNavigation() {
        ExtentReportManager.createTest("Women Category Navigation Test",
                "Verify navigation to Women category");
        ExtentReportManager.logInfo("Clicking on Women category");

        homePage.clickWomenCategory();
        ExtentReportManager.logInfo("Navigated to Women category");

        Assert.assertTrue(driver.getCurrentUrl().contains("women") ||
                        driver.getCurrentUrl().contains("category"),
                "URL should contain women or category");
        ExtentReportManager.logPass("Women category navigation successful");
    }

    @Test(priority = 6, description = "Verify navigation to Accessories category")
    public void testAccessoriesCategoryNavigation() {
        ExtentReportManager.createTest("Accessories Category Navigation Test",
                "Verify navigation to Accessories category");
        ExtentReportManager.logInfo("Clicking on Accessories category");

        homePage.clickAccessoriesCategory();
        ExtentReportManager.logInfo("Navigated to Accessories category");

        Assert.assertTrue(driver.getCurrentUrl().contains("accessories") ||
                        driver.getCurrentUrl().contains("category"),
                "URL should contain accessories or category");
        ExtentReportManager.logPass("Accessories category navigation successful");
    }

    @Test(priority = 7, description = "Verify cart icon is displayed")
    public void testCartIconDisplay() {
        ExtentReportManager.createTest("Cart Icon Display Test",
                "Verify cart icon is displayed on home page");
        ExtentReportManager.logInfo("Checking cart icon visibility");

        homePage.clickCartIcon();
        ExtentReportManager.logInfo("Cart icon clicked");

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"),
                "Should navigate to cart page");
        ExtentReportManager.logPass("Cart icon is functional");
    }
}
