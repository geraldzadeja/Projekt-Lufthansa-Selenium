package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductListingPage extends BasePage {

    @FindBy(css = ".product-item")
    private List<WebElement> productList;

    @FindBy(css = ".product-item-title")
    private List<WebElement> productTitles;

    @FindBy(css = ".product-item-price")
    private List<WebElement> productPrices;

    @FindBy(css = "select[name='sort']")
    private WebElement sortDropdown;

    @FindBy(css = ".filter-options")
    private WebElement filterSection;

    @FindBy(css = ".pagination")
    private WebElement pagination;

    @FindBy(css = ".breadcrumb")
    private WebElement breadcrumb;

    public ProductListingPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductListingPageLoaded() {
        logger.info("Verifying product listing page is loaded");
        return productList.size() > 0;
    }

    public int getProductCount() {
        int count = productList.size();
        logger.info("Total products displayed: {}", count);
        return count;
    }

    public ProductDetailPage clickFirstProduct() {
        logger.info("Clicking on first product");
        return clickProductByIndex(0);
    }

    public ProductDetailPage clickProductByIndex(int index) {
        logger.info("Clicking on product at index: {}", index);
        if (index < productList.size()) {
            click(productList.get(index));
            return new ProductDetailPage(driver);
        } else {
            logger.error("Invalid product index: {}", index);
            throw new IndexOutOfBoundsException("Product index out of bounds: " + index);
        }
    }

    public ProductDetailPage clickProductByName(String productName) {
        logger.info("Clicking on product: {}", productName);
        for (WebElement title : productTitles) {
            if (getText(title).equalsIgnoreCase(productName)) {
                click(title);
                return new ProductDetailPage(driver);
            }
        }
        logger.error("Product not found: {}", productName);
        throw new RuntimeException("Product not found: " + productName);
    }

    public String getProductTitle(int index) {
        String title = getText(productTitles.get(index));
        logger.info("Product title at index {}: {}", index, title);
        return title;
    }

    public String getProductPrice(int index) {
        String price = getText(productPrices.get(index));
        logger.info("Product price at index {}: {}", index, price);
        return price;
    }

    public boolean isProductDisplayed(String productName) {
        logger.info("Checking if product is displayed: {}", productName);
        for (WebElement title : productTitles) {
            if (getText(title).contains(productName)) {
                logger.info("Product found: {}", productName);
                return true;
            }
        }
        logger.info("Product not found: {}", productName);
        return false;
    }

    public void sortProducts(String sortOption) {
        logger.info("Sorting products by: {}", sortOption);
        click(sortDropdown);
        WebElement option = driver.findElement(By.xpath("//option[text()='" + sortOption + "']"));
        click(option);
    }

    public String getBreadcrumb() {
        String text = getText(breadcrumb);
        logger.info("Breadcrumb: {}", text);
        return text;
    }
}
