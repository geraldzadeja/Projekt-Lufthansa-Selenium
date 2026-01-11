package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductHoverTest extends BaseTest {

    @Test
    public void testProductCardHoverEffect() {
        Actions actions = new Actions(driver);
        WaitHelper waitHelper = new WaitHelper(driver);
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement productCard = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".product-item")));

        String opacityBeforeHover = productCard.getCssValue("opacity");
        String boxShadowBeforeHover = productCard.getCssValue("box-shadow");
        String transformBeforeHover = productCard.getCssValue("transform");
        String borderColorBeforeHover = productCard.getCssValue("border-color");

        actions.moveToElement(productCard).perform();

        boolean styleChanged = waitHelper.waitForStyleChange(productCard, "box-shadow", boxShadowBeforeHover, 2) ||
                              waitHelper.waitForStyleChange(productCard, "transform", transformBeforeHover, 2) ||
                              waitHelper.waitForStyleChange(productCard, "opacity", opacityBeforeHover, 2) ||
                              waitHelper.waitForStyleChange(productCard, "border-color", borderColorBeforeHover, 2);

        String opacityAfterHover = productCard.getCssValue("opacity");
        String boxShadowAfterHover = productCard.getCssValue("box-shadow");
        String transformAfterHover = productCard.getCssValue("transform");
        String borderColorAfterHover = productCard.getCssValue("border-color");

        boolean anyPropertyChanged = !opacityBeforeHover.equals(opacityAfterHover) ||
                                     !boxShadowBeforeHover.equals(boxShadowAfterHover) ||
                                     !transformBeforeHover.equals(transformAfterHover) ||
                                     !borderColorBeforeHover.equals(borderColorAfterHover);

        Assert.assertTrue(anyPropertyChanged,
            "CSS properties should change on hover. Before: opacity=" + opacityBeforeHover +
            ", box-shadow=" + boxShadowBeforeHover + ", transform=" + transformBeforeHover +
            ", border-color=" + borderColorBeforeHover +
            " | After: opacity=" + opacityAfterHover +
            ", box-shadow=" + boxShadowAfterHover + ", transform=" + transformAfterHover +
            ", border-color=" + borderColorAfterHover);
    }

    @Test
    public void testProductImageHoverEffect() {
        Actions actions = new Actions(driver);
        WaitHelper waitHelper = new WaitHelper(driver);
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement productImage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".product-item img")));

        String scaleBeforeHover = productImage.getCssValue("transform");
        String opacityBeforeHover = productImage.getCssValue("opacity");
        String filterBeforeHover = productImage.getCssValue("filter");

        actions.moveToElement(productImage).perform();

        boolean styleChanged = waitHelper.waitForStyleChange(productImage, "transform", scaleBeforeHover, 2) ||
                              waitHelper.waitForStyleChange(productImage, "opacity", opacityBeforeHover, 2) ||
                              waitHelper.waitForStyleChange(productImage, "filter", filterBeforeHover, 2);

        String scaleAfterHover = productImage.getCssValue("transform");
        String opacityAfterHover = productImage.getCssValue("opacity");
        String filterAfterHover = productImage.getCssValue("filter");

        boolean imageStyleChanged = !scaleBeforeHover.equals(scaleAfterHover) ||
                                   !opacityBeforeHover.equals(opacityAfterHover) ||
                                   !filterBeforeHover.equals(filterAfterHover);

        Assert.assertTrue(imageStyleChanged,
            "Image CSS properties should change on hover. Before: transform=" + scaleBeforeHover +
            ", opacity=" + opacityBeforeHover + ", filter=" + filterBeforeHover +
            " | After: transform=" + scaleAfterHover +
            ", opacity=" + opacityAfterHover + ", filter=" + filterAfterHover);
    }
}
