package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.ProductFilterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductFilterTest extends BaseTest {

    @Test
    public void testColorFilterWithBlueBorder() {
        ProductFilterPage filterPage = new ProductFilterPage(driver);

        filterPage.applyBlueColorFilter();

        Assert.assertTrue(filterPage.isBlueFilterApplied(),
            "Blue color filter should be applied with blue border on selected swatch");

        int productCount = filterPage.getProductCount();
        Assert.assertTrue(productCount > 0,
            "Product count should be greater than 0 after applying blue filter. Found: " + productCount);
    }

    @Test
    public void testPriceFilterAndValidation() {
        ProductFilterPage filterPage = new ProductFilterPage(driver);

        double minPrice = 0.00;
        double maxPrice = 99.99;

        filterPage.applyPriceFilter(minPrice, maxPrice);

        int productCount = filterPage.getProductCount();
        Assert.assertTrue(productCount > 0,
            "Product count should be greater than 0 after applying price filter. Found: " + productCount);

        Assert.assertTrue(filterPage.areAllPricesWithinRange(minPrice, maxPrice),
            "All product prices should be within the range $" + minPrice + " - $" + maxPrice);
    }

    @Test
    public void testSortByPriceAscending() {
        ProductFilterPage filterPage = new ProductFilterPage(driver);

        filterPage.sortByPrice("Price: Low to High");

        Assert.assertTrue(filterPage.areProductsSortedByPriceAscending(),
            "Products should be sorted in ascending order by price");
    }

    @Test
    public void testSortByPriceDescending() {
        ProductFilterPage filterPage = new ProductFilterPage(driver);

        filterPage.sortByPrice("Price: High to Low");

        Assert.assertTrue(filterPage.areProductsSortedByPriceDescending(),
            "Products should be sorted in descending order by price");
    }

    @Test
    public void testCombinedFiltersAndSort() {
        ProductFilterPage filterPage = new ProductFilterPage(driver);

        filterPage.applyBlueColorFilter();
        Assert.assertTrue(filterPage.isBlueFilterApplied(),
            "Blue color filter should be applied");

        filterPage.applyPriceFilter(0.00, 99.99);

        int productCount = filterPage.getProductCount();
        Assert.assertTrue(productCount > 0,
            "Products should be displayed after applying filters");

        Assert.assertTrue(filterPage.areAllPricesWithinRange(0.00, 99.99),
            "All prices should be within $0.00 - $99.99");

        filterPage.sortByPrice("Price: Low to High");

        Assert.assertTrue(filterPage.areProductsSortedByPriceAscending(),
            "Products should be sorted in ascending order");
    }
}
