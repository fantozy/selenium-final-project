import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static ge.tbcitacademy.data.Constants.cottageUrl;
import static ge.tbcitacademy.data.Constants.offersUrl;
import static org.testng.AssertJUnit.assertTrue;

public class HolidayPageUtils {
    public static void navigateToCategoryPage(WebDriver driver) {
        driver.findElement(By.xpath("//a[text()='დასვენება']")).click();
    }
    public static void chooseFilterOption(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//div[@id='sidebar']//label[text()='კოტეჯი']")).click();
        Thread.sleep(3000);
    }

    public static int getMaxOrMinPriceFromAllPages(WebDriver driver, String type) throws MalformedURLException {
        int initialValue = type.equals("max") ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        List<WebElement> links = driver.findElements(By.cssSelector("div.pager-filter"));
        int lastPageValue = getLastPageValue(links.get(links.size() - 1));

        int result = initialValue;
        for (int page = 1; page <= lastPageValue; page++) {
            List<WebElement> pricesElements = getPricesElements(driver);
            for (WebElement priceElement : pricesElements) {
                int priceValue = getPriceValue(priceElement);
                result = type.equals("max") ? Math.max(result, priceValue) : Math.min(result, priceValue);
            }
            String nextPageUrl = offersUrl + page;
            driver.get(nextPageUrl);
        }
        return result;
    }
    public static void sortPrices(WebDriver driver, String sortType) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//select[@id='sort']")).click();
        driver.findElement(By.xpath("//select[@id='sort']//option[contains(text(), '" + sortType + "' )]")).click();
        Thread.sleep(3000);
    }

    public static void verifyMaxPriceAfterSorting(WebDriver driver, int maxPrice) {
        WebElement firstPriceAfterSort = driver.findElement(By.xpath("//section//div[@class='special-offer']//div[@class='discounted-prices']//p[@class='deal-voucher-price'][1]"));
        int firstPriceValue = Integer.parseInt(firstPriceAfterSort.getText().replaceAll("[^0-9]", ""));
        Assert.assertEquals(maxPrice, firstPriceValue);
    }
    public static void verifyMinPriceAfterSorting(WebDriver driver, int minPrice) {
        WebElement firstPriceAfterSort = driver.findElement(By.xpath("//section//div[@class='special-offer']//div[@class='discounted-prices']//p[@class='deal-voucher-price'][1]"));
        int firstPriceValue = Integer.parseInt(firstPriceAfterSort.getText().replaceAll("[^0-9]", ""));
        Assert.assertEquals(minPrice, firstPriceValue);
    }

    public static boolean validateWordInDescription(WebDriver driver, String word) throws MalformedURLException {
        List<WebElement> links = driver.findElements(By.xpath("//div[@class='pager-filter']"));
        WebElement lastPage = links.get(links.size() - 1);
        int lastPageValue = getLastPageValue(lastPage);
        boolean allOffersContainWord = true;

        for (int page=1; page<=lastPageValue; page++) {
            List<WebElement> offerDescriptionText = getOfferDescriptionText(driver);
            for (WebElement webElement : offerDescriptionText) {
                String descriptionText = webElement.getText();
                if (!descriptionText.contains(word)) {
                    allOffersContainWord = false;
                }
            }
            String nextPageUrl = cottageUrl + page;
            driver.get(nextPageUrl);
        }
        return allOffersContainWord;
    }

    public static void setPriceRange(WebDriver driver, String minPrice, String maxPrice) throws InterruptedException {
        WebElement priceBar = driver.findElement(By.xpath("//div[@id='sidebar']//div[@class='price-filter']"));
        WebElement minPriceField = priceBar.findElement(By.id("minprice"));
        WebElement maxPriceField = priceBar.findElement(By.id("maxprice"));
        minPriceField.sendKeys(minPrice);
        maxPriceField.sendKeys(maxPrice);
        driver.findElement(By.xpath("//div[@id='sidebar']//div[@class='submit-button']")).click();
        Thread.sleep(3000);
    }

    public static boolean verifyPricesInRange(WebDriver driver, int minPrice, int maxPrice) throws MalformedURLException {
        List<WebElement> links = driver.findElements(By.cssSelector("div.pager-filter"));
        int lastPageValue;
        if (!links.isEmpty())  {
            lastPageValue = getLastPageValue(links.get(links.size() - 1));
        } else {
            lastPageValue = 1;
        }
        String nextPageUrl = driver.getCurrentUrl() + "&page=";
        for (int page = 1; page <= lastPageValue; page++) {
            List<WebElement> pricesElements = getPricesElements(driver);
            if (pricesElements.isEmpty()) {
                throw new RuntimeException("Price elements not found on page " + page);
            }

            for (WebElement priceElement : pricesElements) {
                int priceValue = getPriceValue(priceElement);
                if (priceValue < minPrice || priceValue > maxPrice) {
                    throw new RuntimeException("Price value " + priceValue + " is not within the specified range [" + minPrice + ", " + maxPrice + "]");
                }
            }
            driver.get(nextPageUrl + page);
        }
        return true;
    }

    private static List<WebElement> getPricesElements(WebDriver driver) {return driver.findElements(By.xpath("//section//div[@class='special-offer']//div[@class='discounted-prices']//p[@class='deal-voucher-price'][1]"));}
    private static List<WebElement> getOfferDescriptionText(WebDriver driver) {return driver.findElements(By.xpath("//div[@class='special-offer']//div[contains(@class, 'special-offer-text')]"));}
    private static int getPriceValue(WebElement priceElement) {
        String priceText = priceElement.getText();
        return Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    }

    private static int getLastPageValue(WebElement lastPage) throws MalformedURLException {
        WebElement aTag = lastPage.findElement(By.tagName("a"));
        String href = aTag.getAttribute("href");

        URL url = new URL(href);
        String pageParam = StringUtils.substringAfter(url.getQuery(), "page=");
        return Integer.parseInt(pageParam);
    }
}
