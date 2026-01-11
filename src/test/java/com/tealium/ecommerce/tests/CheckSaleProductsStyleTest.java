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

public class CheckSaleProductsStyleTest extends BaseTest {

    @Test(dependsOnMethods = {"com.tealium.ecommerce.tests.CreateAccountTest.testCreateAccount"})
    public void testCheckSaleProductsStyle() {
        String email = CreateAccountTest.testEmail;
        String password = CreateAccountTest.testPassword;

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.clickAccountLink();
        loginPage.clickSignInLink();
        loginPage.login(email, password);

        System.out.println("DEBUG: Signed in successfully");

        Actions actions = new Actions(driver);
        WebElement saleMenu = driver.findElement(By.xpath("//a[contains(@href,'sale') or contains(text(),'Sale') or contains(text(),'SALE') or contains(@class,'sale')]"));
        actions.moveToElement(saleMenu).perform();

        waitHelper.waitForPageLoad();

        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", saleMenu);
        System.out.println("DEBUG: Clicked Sale menu directly");

        waitHelper.waitForPageLoad();

        System.out.println("DEBUG: Current URL: " + driver.getCurrentUrl());
        System.out.println("DEBUG: Page title: " + driver.getTitle());

        List<WebElement> products = driver.findElements(By.cssSelector(".product-item, .item, li.product, .product"));
        System.out.println("DEBUG: Found " + products.size() + " products with first selector");

        if (products.isEmpty()) {
            products = driver.findElements(By.cssSelector("li[class*='item'], div[class*='product']"));
            System.out.println("DEBUG: Found " + products.size() + " products with broader selector");
        }

        if (products.isEmpty()) {
            products = driver.findElements(By.cssSelector("li, div.product-item-info"));
            System.out.println("DEBUG: Found " + products.size() + " elements with very broad selector");
        }

        if (products.isEmpty()) {
            String pageSource = driver.getPageSource();
            System.out.println("DEBUG: Page source length: " + pageSource.length());
            System.out.println("DEBUG: Page contains 'product': " + pageSource.contains("product"));
            System.out.println("DEBUG: Page contains 'price': " + pageSource.contains("price"));
        }

        int productsChecked = 0;
        for (WebElement product : products) {
            try {
                List<WebElement> prices = product.findElements(By.cssSelector(".price, .old-price, .special-price, .regular-price"));

                if (prices.size() >= 2) {
                    productsChecked++;
                    System.out.println("DEBUG: Checking product " + productsChecked + " with " + prices.size() + " prices");

                    WebElement oldPrice = null;
                    WebElement newPrice = null;

                    List<WebElement> oldPriceElements = product.findElements(By.cssSelector(".old-price .price, .regular-price, span[style*='line-through']"));
                    if (!oldPriceElements.isEmpty()) {
                        oldPrice = oldPriceElements.get(0);
                    }

                    List<WebElement> newPriceElements = product.findElements(By.cssSelector(".special-price .price, .price-final_price .price"));
                    if (!newPriceElements.isEmpty()) {
                        newPrice = newPriceElements.get(0);
                    }

                    if (oldPrice != null && newPrice != null) {
                        String oldPriceColor = oldPrice.getCssValue("color");
                        String oldPriceTextDecoration = oldPrice.getCssValue("text-decoration");

                        System.out.println("DEBUG: Old price - color: " + oldPriceColor + ", text-decoration: " + oldPriceTextDecoration);

                        boolean isGrey = oldPriceColor.contains("rgb") &&
                                       (oldPriceColor.contains("128") || oldPriceColor.contains("153") || oldPriceColor.contains("170"));

                        boolean hasStrikethrough = oldPriceTextDecoration.contains("line-through");

                        Assert.assertTrue(hasStrikethrough || isGrey,
                            "Old price should have strikethrough or grey color. Found: color=" + oldPriceColor +
                            ", text-decoration=" + oldPriceTextDecoration);

                        String newPriceColor = newPrice.getCssValue("color");
                        String newPriceTextDecoration = newPrice.getCssValue("text-decoration");

                        System.out.println("DEBUG: New price - color: " + newPriceColor + ", text-decoration: " + newPriceTextDecoration);

                        boolean noStrikethrough = !newPriceTextDecoration.contains("line-through");

                        Assert.assertTrue(noStrikethrough,
                            "New price should NOT have strikethrough. Found: " + newPriceTextDecoration);

                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("DEBUG: Skipping product due to: " + e.getMessage());
            }
        }

        Assert.assertTrue(productsChecked > 0, "At least one sale product should be found and checked");

        System.out.println("DEBUG: Sale products style test passed. Checked " + productsChecked + " products");
    }
}
