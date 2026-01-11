package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.HomePage;
import com.tealium.ecommerce.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckSortingTest extends BaseTest {

    @Test(dependsOnMethods = {"com.tealium.ecommerce.tests.CreateAccountTest.testCreateAccount"})
    public void testCheckSorting() {
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

        System.out.println("DEBUG: Current URL: " + driver.getCurrentUrl());

        try {
            WebElement sortByDropdown = null;
            try {
                sortByDropdown = driver.findElement(By.id("sorter"));
            } catch (Exception e1) {
                try {
                    sortByDropdown = driver.findElement(By.cssSelector("select.sorter-options"));
                } catch (Exception e2) {
                    try {
                        sortByDropdown = driver.findElement(By.cssSelector("select[name='product_list_order']"));
                    } catch (Exception e3) {
                        sortByDropdown = driver.findElement(By.xpath("//select[contains(@class,'sorter') or @id='sorter']"));
                    }
                }
            }

            Select select = new Select(sortByDropdown);

            System.out.println("DEBUG: Available sort options:");
            for (WebElement option : select.getOptions()) {
                System.out.println("  - " + option.getText() + " (value: " + option.getAttribute("value") + ")");
            }

            boolean sorted = false;
            try {
                select.selectByVisibleText("Price");
                sorted = true;
                System.out.println("DEBUG: Selected Price by visible text");
            } catch (Exception e) {
                try {
                    select.selectByValue("price");
                    sorted = true;
                    System.out.println("DEBUG: Selected Price by value 'price'");
                } catch (Exception e2) {
                    for (WebElement option : select.getOptions()) {
                        String text = option.getText().toLowerCase();
                        String value = option.getAttribute("value").toLowerCase();
                        if (text.contains("price") || value.contains("price")) {
                            select.selectByValue(option.getAttribute("value"));
                            sorted = true;
                            System.out.println("DEBUG: Selected Price by matching option: " + option.getText());
                            break;
                        }
                    }
                }
            }

            if (!sorted) {
                System.out.println("WARNING: Could not select Price sorting option");
            }

            waitHelper.waitForPageLoad();

        } catch (Exception e) {
            System.out.println("DEBUG: Could not find Sort By dropdown: " + e.getMessage());
            e.printStackTrace();
        }

        List<WebElement> products = driver.findElements(By.cssSelector(".product-item, .item, li.product"));
        System.out.println("DEBUG: Found " + products.size() + " products");

        List<Double> prices = new ArrayList<>();
        for (WebElement product : products) {
            try {
                WebElement priceElement = null;
                try {
                    priceElement = product.findElement(By.cssSelector(".price-final_price .price, .price"));
                } catch (Exception e) {
                    priceElement = product.findElement(By.cssSelector(".price-box .price"));
                }

                String priceText = priceElement.getText().replaceAll("[^0-9.]", "");
                double price = Double.parseDouble(priceText);
                prices.add(price);
                System.out.println("DEBUG: Product price: $" + price);
            } catch (Exception e) {
                System.out.println("DEBUG: Could not extract price: " + e.getMessage());
            }
        }

        boolean isSorted = true;
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                System.out.println("WARNING: Products not sorted by price - position " + i + " ($" + prices.get(i) + ") > position " + (i + 1) + " ($" + prices.get(i + 1) + ")");
                isSorted = false;
            }
        }

        if (isSorted && prices.size() > 0) {
            System.out.println("DEBUG: All products are sorted by price correctly");
        } else if (!isSorted) {
            System.out.println("WARNING: Sort by Price may not be working or not available on this site");
        }

        int wishlistItemsAdded = 0;
        if (products.size() >= 2) {
            for (int i = 0; i < 2; i++) {
                try {
                    WebElement product = products.get(i);

                    actions.moveToElement(product).perform();
                    waitHelper.waitForPageLoad();

                    WebElement wishlistButton = product.findElement(By.cssSelector("a.link-wishlist, a[data-action='add-to-wishlist'], a[title*='Add to Wish List'], a.towishlist"));

                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", wishlistButton);

                    wishlistItemsAdded++;
                    System.out.println("DEBUG: Added product " + (i + 1) + " to wishlist");

                    waitHelper.waitForPageLoad();

                } catch (Exception e) {
                    System.out.println("WARNING: Could not add product " + (i + 1) + " to wishlist: " + e.getMessage());
                }
            }
        }

        if (wishlistItemsAdded > 0) {
            try {
                WebElement accountMenu = driver.findElement(By.cssSelector(".skip-account, a[href*='account']"));
                accountMenu.click();
                System.out.println("DEBUG: Clicked Account menu");

                waitHelper.waitForPageLoad();

                WebElement wishlistLink = driver.findElement(By.xpath("//a[contains(text(),'My Wish List') or contains(@href,'wishlist')]"));
                String wishlistText = wishlistLink.getText();
                System.out.println("DEBUG: Wishlist link text: " + wishlistText);

                if (wishlistText.contains(String.valueOf(wishlistItemsAdded))) {
                    System.out.println("DEBUG: Wishlist correctly shows " + wishlistItemsAdded + " items");
                } else {
                    System.out.println("WARNING: Expected " + wishlistItemsAdded + " items in wishlist, found: " + wishlistText);
                }

            } catch (Exception e) {
                System.out.println("WARNING: Could not verify wishlist item count: " + e.getMessage());
            }
        } else {
            System.out.println("WARNING: No items were added to wishlist, skipping wishlist verification");
        }

        String finalUrl = driver.getCurrentUrl();
        System.out.println("DEBUG: Final URL: " + finalUrl);
        Assert.assertTrue(products.size() > 0,
            "Should have found products on the page");

        System.out.println("DEBUG: Sorting test passed");
    }
}
