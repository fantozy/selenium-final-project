import org.testng.Assert;
import org.testng.annotations.Test;
import config.config;
import java.net.MalformedURLException;


import static ge.tbcitacademy.data.Constants.*;
import static org.testng.AssertJUnit.assertTrue;

public class HolidayPageTests extends config {
    @Test
    public void descendingOrderTest() throws MalformedURLException, InterruptedException {
        driver.get(swoopUrl);
        HolidayPageUtils.navigateToCategoryPage(driver);
        int maxPrice = HolidayPageUtils.getMaxOrMinPriceFromAllPages(driver, "max");
        HolidayPageUtils.sortPrices(driver, sortByDescending);
        HolidayPageUtils.verifyMaxPriceAfterSorting(driver, maxPrice);
    }
    @Test
    public void ascendingOrderTest() throws MalformedURLException, InterruptedException {
        driver.get(swoopUrl);
        HolidayPageUtils.navigateToCategoryPage(driver);
        int minPrice = HolidayPageUtils.getMaxOrMinPriceFromAllPages(driver, "min");
        HolidayPageUtils.sortPrices(driver, sortByAscending);
        HolidayPageUtils.verifyMinPriceAfterSorting(driver, minPrice);
    }
    @Test
    public void filterTest() throws InterruptedException, MalformedURLException {
        driver.get(swoopUrl);
        HolidayPageUtils.navigateToCategoryPage(driver);
        HolidayPageUtils.chooseFilterOption(driver);
        boolean allOffersContainsWord = HolidayPageUtils.validateWordInDescription(driver, containsWord);
        Assert.assertTrue(allOffersContainsWord);
        int minPrice = HolidayPageUtils.getMaxOrMinPriceFromAllPages(driver, "min");
        HolidayPageUtils.sortPrices(driver, sortByAscending);
        HolidayPageUtils.verifyMinPriceAfterSorting(driver, minPrice);
    }
    @Test
    public void priceRangeTest() throws InterruptedException, MalformedURLException {
        driver.get(swoopUrl);
        HolidayPageUtils.navigateToCategoryPage(driver);
        HolidayPageUtils.setPriceRange(driver, minPriceString, maxPriceString);
        assertTrue(HolidayPageUtils.verifyPricesInRange(driver, minPrice, maxPrice));
    }
}

