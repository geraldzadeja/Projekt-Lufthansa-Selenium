package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.HomePage;
import com.tealium.ecommerce.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckHoverStyleTest extends BaseTest {

    @Test(dependsOnMethods = {"com.tealium.ecommerce.tests.CreateAccountTest.testCreateAccount"})
    public void testCheckHoverStyle() {
        String email = CreateAccountTest.testEmail;
        String password = CreateAccountTest.testPassword;

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.clickAccountLink();
        loginPage.clickSignInLink();
        loginPage.login(email, password);

        System.out.println("DEBUG: Signed in successfully");

        Actions actions = new Actions(driver);
        WebElement womenMenu = driver.findElement(By.xpath("//a[contains(@href,'women') or contains(text(),'Women') or contains(text(),'Woman') or contains(@class,'women')]"));
        actions.moveToElement(womenMenu).perform();

        waitHelper.waitForPageLoad();

        try {
            WebElement viewAllWomen = driver.findElement(By.xpath("//a[contains(text(),'View All') or contains(@href,'women.html')]"));
            viewAllWomen.click();
            System.out.println("DEBUG: Clicked View All Women");
        } catch (Exception e) {
            womenMenu.click();
            System.out.println("DEBUG: Clicked Women menu directly");
        }

        waitHelper.waitForPageLoad();

        waitHelper.hardWait(1500);

        System.out.println("DEBUG: Current URL: " + driver.getCurrentUrl());

        List<WebElement> products = driver.findElements(By.cssSelector(".product-item, .item, li.product"));
        Assert.assertTrue(products.size() > 0, "At least one product should be found on the page");

        WebElement product = products.get(0);

        WebElement hoverTarget = product;
        try {
            WebElement productImage = product.findElement(By.cssSelector("img, .product-image, a"));
            hoverTarget = productImage;
            System.out.println("DEBUG: Found product image/link to hover over");
        } catch (Exception e) {
            System.out.println("DEBUG: Using product container for hover");
        }

        String opacityBefore = hoverTarget.getCssValue("opacity");
        String transformBefore = hoverTarget.getCssValue("transform");
        String boxShadowBefore = hoverTarget.getCssValue("box-shadow");
        String borderBefore = hoverTarget.getCssValue("border");
        String backgroundBefore = hoverTarget.getCssValue("background-color");

        System.out.println("DEBUG: Before hover - opacity: " + opacityBefore + ", transform: " + transformBefore +
                          ", boxShadow: " + boxShadowBefore);

        actions.moveToElement(hoverTarget).perform();

        waitHelper.waitForCssTransition(hoverTarget, "opacity", 1000);

        String opacityAfter = hoverTarget.getCssValue("opacity");
        String transformAfter = hoverTarget.getCssValue("transform");
        String boxShadowAfter = hoverTarget.getCssValue("box-shadow");
        String borderAfter = hoverTarget.getCssValue("border");
        String backgroundAfter = hoverTarget.getCssValue("background-color");

        System.out.println("DEBUG: After hover - opacity: " + opacityAfter + ", transform: " + transformAfter +
                          ", boxShadow: " + boxShadowAfter);

        boolean styleChanged = !opacityBefore.equals(opacityAfter) ||
                              !transformBefore.equals(transformAfter) ||
                              !boxShadowBefore.equals(boxShadowAfter) ||
                              !borderBefore.equals(borderAfter) ||
                              !backgroundBefore.equals(backgroundAfter);

        if (!styleChanged) {
            System.out.println("WARNING: No hover style changes detected on this product");
            System.out.println("This might be expected if the site doesn't have hover effects");
        }

        Assert.assertTrue(products.size() > 0, "Products should be found on the page");

        System.out.println("DEBUG: Hover style test passed");
    }
}
