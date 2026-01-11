package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.SaleProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SalePricingStyleTest extends BaseTest {

    @Test
    public void testSaleProductPricingStyles() {
        SaleProductPage saleProductPage = new SaleProductPage(driver);

        Assert.assertTrue(saleProductPage.areTwoPricesDisplayed(),
            "Two prices (original and final) should be displayed for sale products");

        Assert.assertTrue(saleProductPage.isOriginalPriceStrikethrough(),
            "Original price should have strikethrough text decoration. Actual: " +
            saleProductPage.getOriginalPriceTextDecoration());

        Assert.assertTrue(saleProductPage.isOriginalPriceGrey(),
            "Original price should be grey color. Actual color: " +
            saleProductPage.getOriginalPriceColor());

        Assert.assertTrue(saleProductPage.isFinalPriceBlue(),
            "Final price should be blue color. Actual color: " +
            saleProductPage.getFinalPriceColor());

        Assert.assertTrue(saleProductPage.isFinalPriceNotStrikethrough(),
            "Final price should NOT have strikethrough. Actual: " +
            saleProductPage.getFinalPriceTextDecoration());
    }
}
