package com.tealium.ecommerce.pages;

import com.tealium.ecommerce.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SaleProductPage extends BasePage {

    @FindBy(css = ".product-item .price")
    private List<WebElement> productPrices;

    @FindBy(css = ".product-item .old-price .price")
    private WebElement originalPrice;

    @FindBy(css = ".product-item .special-price .price")
    private WebElement finalPrice;

    public SaleProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean areTwoPricesDisplayed() {
        logger.info("Checking if two prices are displayed");
        return isDisplayed(originalPrice) && isDisplayed(finalPrice);
    }

    public int getPriceCount() {
        logger.info("Getting price count");
        return productPrices.size();
    }

    public boolean isOriginalPriceStrikethrough() {
        logger.info("Checking if original price has strikethrough");
        String textDecoration = originalPrice.getCssValue("text-decoration");
        String textDecorationLine = originalPrice.getCssValue("text-decoration-line");

        boolean hasStrikethrough = textDecoration.contains("line-through") ||
                                  textDecorationLine.contains("line-through");
        logger.info("Original price text-decoration: {}, text-decoration-line: {}",
                   textDecoration, textDecorationLine);
        return hasStrikethrough;
    }

    public boolean isOriginalPriceGrey() {
        logger.info("Checking if original price is grey");
        String color = originalPrice.getCssValue("color");
        logger.info("Original price color: {}", color);

        return color.contains("128") || color.contains("153") ||
               color.contains("grey") || color.contains("gray") ||
               isRgbGrey(color);
    }

    public boolean isFinalPriceBlue() {
        logger.info("Checking if final price is blue");
        String color = finalPrice.getCssValue("color");
        logger.info("Final price color: {}", color);

        return color.contains("blue") || isRgbBlue(color);
    }

    public boolean isFinalPriceNotStrikethrough() {
        logger.info("Checking if final price does not have strikethrough");
        String textDecoration = finalPrice.getCssValue("text-decoration");
        String textDecorationLine = finalPrice.getCssValue("text-decoration-line");

        boolean hasNoStrikethrough = !textDecoration.contains("line-through") &&
                                     !textDecorationLine.contains("line-through");
        logger.info("Final price text-decoration: {}, text-decoration-line: {}",
                   textDecoration, textDecorationLine);
        return hasNoStrikethrough;
    }

    public String getOriginalPriceColor() {
        logger.info("Getting original price color");
        return originalPrice.getCssValue("color");
    }

    public String getFinalPriceColor() {
        logger.info("Getting final price color");
        return finalPrice.getCssValue("color");
    }

    public String getOriginalPriceTextDecoration() {
        logger.info("Getting original price text decoration");
        return originalPrice.getCssValue("text-decoration");
    }

    public String getFinalPriceTextDecoration() {
        logger.info("Getting final price text decoration");
        return finalPrice.getCssValue("text-decoration");
    }

    private boolean isRgbGrey(String color) {
        if (!color.startsWith("rgb")) {
            return false;
        }

        try {
            String[] rgb = color.replace("rgba(", "").replace("rgb(", "")
                               .replace(")", "").split(",");
            int r = Integer.parseInt(rgb[0].trim());
            int g = Integer.parseInt(rgb[1].trim());
            int b = Integer.parseInt(rgb[2].trim());

            return Math.abs(r - g) <= 20 && Math.abs(g - b) <= 20 && Math.abs(r - b) <= 20;
        } catch (Exception e) {
            logger.error("Error parsing RGB color: {}", e.getMessage());
            return false;
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
