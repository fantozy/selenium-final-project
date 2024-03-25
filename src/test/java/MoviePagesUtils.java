import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static ge.tbcitacademy.data.Constants.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class MoviePagesUtils {
    public static void buyMovie(WebDriver driver) {
        List<WebElement> movies = driver.findElements(By.xpath("//div[@class='movies-deal']"));
        WebElement movie = movies.get(0);
        Actions actions = new Actions(driver);
        actions.moveToElement(movie).perform();
        movie.findElement(By.xpath("//div[@class='movies-deal']//p[text()='ყიდვა']")).click();
    }
    public static void choseLocation(WebDriver driver) {
        WebElement caveaElement = driver.findElement(By.xpath("//a[text()='კავეა ისთ ფოინთი']"));
        caveaElement.click();
    }
    public static void checkLocation(WebDriver driver) {
        List<WebElement> cinemas = driver.findElements(By.xpath("//div[contains(@class, 'all-cinemas')]//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and not(contains(@style, 'display: none'))]//div//div[not(contains(@style, 'display: none'))]"));
        List<WebElement> cinemasWithCavea = driver.findElements(By.xpath("//div[contains(@class, 'all-cinemas')]//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and not(contains(@style, 'display: none'))]//div//div[not(contains(@style, 'display: none'))]//p[text()='კავეა ისთ ფოინთი']"));
        assertEquals(cinemas.size(), cinemasWithCavea.size());
    }
    public static void choseLastDate(WebDriver driver) {
        lastDateTimeElement(driver).click();
    }
    public static void choseLastMovie(WebDriver driver) throws InterruptedException {
        lastMovieElement(driver).click();
        Thread.sleep(1000);
    }

    public static WebElement lastDateTimeElement(WebDriver driver) {
        List<WebElement> dates = driver.findElements(By.xpath("//div[contains(@class, 'all-cinemas')]//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and not(contains(@style, 'display: none'))]//ul[@class='tabs ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all']"));
        return dates.get(dates.size()-1);
    }

    public static WebElement lastMovieElement(WebDriver driver) {
        List<WebElement> newCinemasWithCavea = driver.findElements(By.xpath("//div[contains(@class, 'all-cinemas')]//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and not(contains(@style, 'display: none'))]//div//div[not(contains(@style, 'display: none'))]//p[text()='კავეა ისთ ფოინთი']"));
        return newCinemasWithCavea.get(newCinemasWithCavea.size()-1);

    }
    public static String getMovieName(WebDriver driver) {
        return driver.findElement(By.xpath("//p[@class='name']")).getText();
    }
    public static String getLocation(WebDriver driver) {
        return driver.findElement(By.xpath("//div[contains(@class, 'all-cinemas')]//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and not(contains(@style, 'display: none'))]//div//div[not(contains(@style, 'display: none'))]//p[@class='cinema-title']")).getText();
    }
    public static String getDate(WebElement element) {
        String date = element.findElement(By.xpath(".//a")).getText();
        return date.replaceAll("[^0-9]", "");
    }

    public static String getTime(WebDriver driver) {
        List<WebElement> newCinemasWithCavea = driver.findElements(By.xpath("//div[contains(@class, 'all-cinemas')]//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and not(contains(@style, 'display: none'))]//div//div[not(contains(@style, 'display: none'))]"));
        WebElement element = newCinemasWithCavea.get(newCinemasWithCavea.size()-1);
        return element.findElement(By.xpath(".//p[1]")).getText();
    }
    public static void checkValues(WebDriver driver, String expectedName, String expectedLocation, String expectedDate, String expectedTime) {
        String movieName = driver.findElement(By.xpath("//p[@class='movie-title']")).getText();
        String locationName = driver.findElement(By.xpath("//p[@class='movie-cinema'][1]")).getText();
        String movieDate = driver.findElement(By.xpath("//p[@class='movie-cinema'][2]")).getText();
        assertTrue(movieName.equalsIgnoreCase(expectedName));
        assertTrue(locationName.equalsIgnoreCase(expectedLocation));
        assertTrue(movieDate.contains(expectedDate));
        assertTrue(movieDate.contains(expectedTime));
    }
    public static void checkSeat(WebDriver driver) throws InterruptedException {
        WebElement freeSeat;
        try {
            freeSeat = driver.findElement(By.xpath("//div[@class='seance']//div[@class='seat free']"));
        } catch (Exception e) {
            throw new RuntimeException("NO FREE SEATS");
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", freeSeat);

        freeSeat.click();
        Thread.sleep(1000);
    }
    public static void registration(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement registerButton = driver.findElement(By.xpath("//div[contains(@class, 'create')]"));
        wait.until(ExpectedConditions.visibilityOf(registerButton));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", registerButton);

        Thread.sleep(1000);

        try {
            registerButton.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);
        }
        ((JavascriptExecutor) driver).executeScript("document.getElementById('email').value = '" + incorrectMail + "';");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('password').value = '" + password + "';");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('PasswordRetype').value = '" + password + "';");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('Gender1').click();");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('name').value = '" + name + "';");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('surname').value = '" + surname + "';");
        ((JavascriptExecutor) driver).executeScript("document.querySelector(\"select[name='birth_year']\").value = '2003';");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('Phone').value = '" + phoneNumber + "';");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('PhoneCode').value = '" + code + "';");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('test').click();");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('tbcAgreement').click();");
        WebElement registrationBtn  = driver.findElement(By.xpath("//button[@id='registrationBtn']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registrationBtn);

    }
    public static void validateEmail(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='input-error-email']")));
        String error = element.getText();
        assertTrue(error.equalsIgnoreCase(expectedEmailError) || error.equalsIgnoreCase(expectedEmailErrorEnglish));
    }
}
