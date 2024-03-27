import config.config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static ge.tbcitacademy.data.Constants.swoopUrl;

public class MoviePagesTest  extends  config {
    @Test
    public void MovieTest() throws InterruptedException {
        driver.get(swoopUrl);
        driver.findElement(By.xpath("//a[text()='კინო']")).click();

        MoviePagesUtils.buyMovie(driver);
        String expectedName = MoviePagesUtils.getMovieName(driver);
        MoviePagesUtils.choseLocation(driver);
        MoviePagesUtils.checkLocation(driver);
        String expectedCinema = MoviePagesUtils.getLocation(driver);
        MoviePagesUtils.choseLastDate(driver);

        WebElement lastTime = MoviePagesUtils.lastDateTimeElement(driver);
        String expectedDate = MoviePagesUtils.getDate(lastTime);
        String expectedTime = MoviePagesUtils.getTime(driver);

        MoviePagesUtils.choseLastMovie(driver);
        MoviePagesUtils.checkValues(driver, expectedName, expectedCinema, expectedDate, expectedTime);
        MoviePagesUtils.checkSeat(driver);

        MoviePagesUtils.registration(driver);
        MoviePagesUtils.validateEmail(driver);



    }
}

