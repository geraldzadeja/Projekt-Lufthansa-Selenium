package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = "a.logo")
    private WebElement logo;

    @FindBy(css = "input[name='search']")
    private WebElement searchBox;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//a[contains(text(),'Men')]")
    private WebElement menCategory;

    @FindBy(xpath = "//a[contains(text(),'Women')]")
    private WebElement womenCategory;

    @FindBy(xpath = "//a[contains(text(),'Accessories')]")
    private WebElement accessoriesCategory;

    @FindBy(css = "a.cart-link")
    private WebElement cartIcon;

    @FindBy(css = ".cart-count")
    private WebElement cartCount;

    @FindBy(css = "a[href*='account']")
    private WebElement accountLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHomePageLoaded() {
        logger.info("Verifying home page is loaded");
        return isDisplayed(logo);
    }

    public ProductListingPage searchProduct(String productName) {
        logger.info("Searching for product: {}", productName);
        sendKeys(searchBox, productName);
        click(searchButton);
        return new ProductListingPage(driver);
    }

    public ProductListingPage clickMenCategory() {
        logger.info("Clicking on Men category");
        click(menCategory);
        return new ProductListingPage(driver);
    }

    public ProductListingPage clickWomenCategory() {
        logger.info("Clicking on Women category");
        click(womenCategory);
        return new ProductListingPage(driver);
    }

    public ProductListingPage clickAccessoriesCategory() {
        logger.info("Clicking on Accessories category");
        click(accessoriesCategory);
        return new ProductListingPage(driver);
    }

    public CartPage clickCartIcon() {
        logger.info("Clicking on cart icon");
        click(cartIcon);
        return new CartPage(driver);
    }

    public String getCartCount() {
        logger.info("Getting cart count");
        return getText(cartCount);
    }

    public void clickAccountLink() {
        logger.info("Clicking on account link");
        click(accountLink);
    }

    public boolean isLogoDisplayed() {
        logger.info("Verifying logo is displayed");
        return isDisplayed(logo);
    }
}
