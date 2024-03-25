import config.config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static ge.tbcitacademy.data.Constants.*;

public class LandingPageTests extends config {
    @Test
    public void activeCategoryTest() throws InterruptedException {
        driver.get(swoopUrl);
        LandingPageUtils.hideCookieNotification(driver);
        LandingPageUtils.selectKartingCategory(driver);
        Assert.assertEquals(driver.getCurrentUrl(), cartingUrl);
        WebElement cartingInLeftSide = driver.findElement(By.xpath("//div[@id='sidebar']//span[text()='კარტინგი']"));
        String cartingColorInHex = LandingPageUtils.rgbToHex(cartingInLeftSide.getCssValue("color"));
        Assert.assertTrue(expectedCartingColor.equalsIgnoreCase(cartingColorInHex));
    }
    @Test
    public void logoTest() {
        driver.get(swoopUrl);
        HolidayPageUtils.navigateToCategoryPage(driver);
        LandingPageUtils.clickOnSwoopLogo(driver);
        Assert.assertEquals(driver.getCurrentUrl(), swoopUrl);
    }
}
