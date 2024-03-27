import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class LandingPageUtils {
    static public void hideCookieNotification(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.cookieSection').style.display = 'none';");
    }
    static public void selectKartingCategory(WebDriver driver) {
        WebElement categoriesMenu = driver.findElement(By.xpath("//div[contains(@class, 'NewCategories')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(categoriesMenu).perform();
        categoriesMenu.click();
        WebElement sportCategory = driver.findElement(By.xpath("//li[contains(@cat_id, 'CatId-6')]"));
        actions.moveToElement(sportCategory).perform();
        WebElement carting = driver.findElement(By.xpath("//div[contains(@class, 'subCategory-6')]//a[text()='კარტინგი']"));
        carting.click();
    }
    static public String rgbToHex(String rgba) {
        System.out.println(rgba);
        String[] rgbaArray = rgba.replace("rgba(", "").replace(")", "").split(",");
        int r = Integer.parseInt(rgbaArray[0].trim());
        int g = Integer.parseInt(rgbaArray[1].trim());
        int b = Integer.parseInt(rgbaArray[2].trim());

        return String.format("#%02x%02x%02x", r, g, b);
        }
    static public void clickOnSwoopLogo(WebDriver driver) {
        WebElement logo = driver.findElement(By.xpath("//a[@class='Newlogo']"));
        logo.click();
    }
}


