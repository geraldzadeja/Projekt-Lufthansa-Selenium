package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductFilterPage extends BasePage {

    @FindBy(css = "a.swatch-option[aria-label*='Blue']")
    private WebElement blueColorFilter;

    @FindBy(css = "a.swatch-option.selected")
    private WebElement selectedColorSwatch;

    @FindBy(css = ".filter-options-item:contains('Price') a")
    private List<WebElement> priceFilterOptions;

    @FindBy(xpath = "//div[@class='filter-options-item' and .//span[text()='Price']]//a[contains(text(), '$0.00 - $99.99')]")
    private WebElement priceRangeFilter;

    @FindBy(css = ".product-item")
    private List<WebElement> productItems;

    @FindBy(css = ".product-item .price")
    private List<WebElement> productPrices;

    @FindBy(css = ".toolbar-amount")
    private WebElement productCountText;

    @FindBy(id = "sorter")
    private WebElement sortDropdown;

    public ProductFilterPage(WebDriver driver) {
        super(driver);
    }

    public void applyBlueColorFilter() {
        logger.info("Applying blue color filter");
        click(blueColorFilter);
        waitHelper.waitForElementToBeVisible(selectedColorSwatch);
    }

    public boolean isBlueFilterApplied() {
        logger.info("Verifying blue filter is applied via border color");
        String borderColor = selectedColorSwatch.getCssValue("border-color");
        String borderWidth = selectedColorSwatch.getCssValue("border-width");

        logger.info("Selected swatch border-color: {}, border-width: {}", borderColor, borderWidth);

        boolean hasBorder = !borderWidth.equals("0px");

        boolean isBlueBorder = isRgbBlue(borderColor) || borderColor.contains("blue");

        return hasBorder && isBlueBorder;
    }

    public void applyPriceFilter(double minPrice, double maxPrice) {
        logger.info("Applying price filter: ${} - ${}", minPrice, maxPrice);
        click(priceRangeFilter);
        waitHelper.waitForPageLoad();
    }

    public int getProductCount() {
        logger.info("Getting product count");
        waitHelper.waitForElementToBeVisible(productItems.get(0));
        int count = productItems.size();
        logger.info("Product count: {}", count);
        return count;
    }

    public int getDisplayedProductCount() {
        logger.info("Getting displayed product count from text");
        String countText = getText(productCountText);
        String[] parts = countText.split(" ");
        for (String part : parts) {
            if (part.matches("\\d+")) {
                return Integer.parseInt(part);
            }
        }
        return 0;
    }

    public boolean areAllPricesWithinRange(double minPrice, double maxPrice) {
        logger.info("Verifying all prices are within range ${} - ${}", minPrice, maxPrice);
        waitHelper.waitForElementToBeVisible(productPrices.get(0));

        for (WebElement priceElement : productPrices) {
            String priceText = getText(priceElement);
            double price = extractPriceValue(priceText);

            logger.info("Product price: ${}", price);

            if (price < minPrice || price > maxPrice) {
                logger.error("Price ${} is outside range ${} - ${}", price, minPrice, maxPrice);
                return false;
            }
        }

        logger.info("All prices are within the specified range");
        return true;
    }

    public void sortByPrice(String order) {
        logger.info("Sorting by price: {}", order);
        waitHelper.waitForElementToBeVisible(sortDropdown);
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(order);
        waitHelper.waitForPageLoad();
    }

    public boolean areProductsSortedByPriceAscending() {
        logger.info("Verifying products are sorted by price in ascending order");
        List<Double> prices = getAllPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                logger.error("Products not in ascending order: ${} > ${}",
                            prices.get(i), prices.get(i + 1));
                return false;
            }
        }

        logger.info("Products are correctly sorted in ascending order");
        return true;
    }

    public boolean areProductsSortedByPriceDescending() {
        logger.info("Verifying products are sorted by price in descending order");
        List<Double> prices = getAllPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) < prices.get(i + 1)) {
                logger.error("Products not in descending order: ${} < ${}",
                            prices.get(i), prices.get(i + 1));
                return false;
            }
        }

        logger.info("Products are correctly sorted in descending order");
        return true;
    }

    public List<Double> getAllPrices() {
        logger.info("Getting all product prices");
        waitHelper.waitForElementToBeVisible(productPrices.get(0));

        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : productPrices) {
            String priceText = getText(priceElement);
            double price = extractPriceValue(priceText);
            prices.add(price);
        }

        logger.info("Retrieved {} prices", prices.size());
        return prices;
    }

    private double extractPriceValue(String priceText) {
        String numericPrice = priceText.replaceAll("[^0-9.]", "");
        try {
            return Double.parseDouble(numericPrice);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse price: {}", priceText);
            return 0.0;
        }
    }

    private boolean isRgbBlue(String color) {
        if (!color.startsWith("rgb")) {
            return false;
        }

        try {
            String[] rgb = color.replace("rgba(", "").replace("rgb(", "")
                               .replace(")", "").split(",");
            int r = Integer.parseInt(rgb[0].trim());
            int g = Integer.parseInt(rgb[1].trim());
            int b = Integer.parseInt(rgb[2].trim());

            return b > r && b > g && (b - r > 30 || b - g > 30);
        } catch (Exception e) {
            logger.error("Error parsing RGB color: {}", e.getMessage());
            return false;
        }
    }
}
