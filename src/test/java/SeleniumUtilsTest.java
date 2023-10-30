import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SeleniumUtilsTest {

    public static void waitUntilClickable(WebDriver driver, By by, int seconds){
      new  WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void jsClick(WebDriver driver, By by){
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(by));
    }







}
