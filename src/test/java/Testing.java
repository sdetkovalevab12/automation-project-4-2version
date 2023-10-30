import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = null;

        try{
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            Actions actions = new Actions(driver);
          //  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.get("https://www.airbnb.com/");

            SeleniumUtilsTest.waitUntilClickable(driver, By.xpath("//button[@data-testid='cypress-headernav-profile']"), 5);
            driver.findElement(By.xpath("//button[@data-testid='cypress-headernav-profile']")).click();
            driver.findElement(By.linkText("Log in")).click();
            SeleniumUtilsTest.waitUntilClickable(driver, By.xpath("//button[@data-testid='social-auth-button-email']"), 5);
            driver.findElement(By.xpath("//button[@data-testid='social-auth-button-email']")).click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.findElement(By.xpath("//label[@class='_1yw7hpv']")).sendKeys("tester.in.test.b12@gmail.com", Keys.ENTER);
            driver.findElement(By.name("user[password]")).sendKeys("Test1234!", Keys.ENTER);
            Assert.assertTrue(driver.findElement(By.xpath("//picture[@class=' dir dir-ltr']//img[@class='itu7ddv i1mla2as i1cqnm0r i1de1kle dir dir-ltr']")).isDisplayed());

            SeleniumUtilsTest.jsClick(driver, By.xpath("//div[@data-testid='little-search-icon']"));

            driver.findElement(By.xpath("//input[@data-testid='structured-search-input-field-query']")).sendKeys("Ibiza, Spain", Keys.ENTER);
            driver.findElement(By.xpath("//div[@data-testid='calendar-day-11/06/2023']")).click();
            driver.findElement(By.xpath("//div[@data-testid='calendar-day-11/12/2023']")).click();
            driver.findElement(By.xpath("//div[*='Add guests']//div[2]")).click();
            actions.doubleClick(driver.findElement(By.xpath("//button[@data-testid='stepper-adults-increase-button']"))).perform();
            actions.doubleClick(driver.findElement(By.xpath("//button[@data-testid='stepper-children-increase-button']"))).perform();
            driver.findElement(By.xpath("//button[@data-testid='structured-search-input-search-button']")).click();


            driver.findElement(By.xpath("//button[@data-testid='category-bar-filter-button']")).click();

            driver.findElement(By.xpath("//input[@id='price_filter_min']")).sendKeys("0", Keys.TAB, Keys.chord(Keys.CONTROL, "A", Keys.DELETE),  "600");
            driver.findElement(By.xpath("//footer[@class='f17xk0zk dir dir-ltr']//a")).click();

            List<WebElement> searchResults = driver.findElements(By.xpath("//span[@class='_1y74zjx'] |//span[@class='_tyxjp1']"));
            List<Integer> prices = new ArrayList<>();

            for (WebElement price : searchResults) {
                prices.add(Integer.parseInt(price.getText().replace("$", "").replace(" ", "")));
            }

            for (Integer price: prices){
                Assert.assertTrue(price>=100 && price<=600);
            }

            String pricePerNight= searchResults.get(0).getText();
            String total=driver.findElement(By.xpath("(//div[@class='_tt122m']//span)[1]")).getText().replace(" total", "");
           //System.out.println(total);
            String rating =driver.findElement(By.xpath("//span[@class='r1dxllyb dir dir-ltr']")).getText().substring(0,3);

            driver.findElement(By.xpath("//div[@class='cy5jw6o  dir dir-ltr'][1]")).click();
            String mainWindowHandle= driver.getWindowHandle();
            for(String windowHandle: driver.getWindowHandles()) {
                if(!windowHandle.equals(mainWindowHandle)){
                    driver.switchTo().window(windowHandle);
                }
            }

            driver.findElement(By.xpath("//button[@aria-label='Close']")).click();

            String priceToCompare=driver.findElement(By.xpath("(//span[@class='_1y74zjx'])[2]")).getText();
            Assert.assertEquals(pricePerNight, priceToCompare);
            String ratingToCompare= driver.findElement(By.xpath("//span[@class='_1uaq0z1l']")).getText().substring(0,3);
            Assert.assertEquals(rating, ratingToCompare);
            String totalPrice = (String)((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", driver.findElement(By.xpath("//span[@class='_1qs94rc']//span[@class='_j1kt73']")));
            //System.out.println(totalPrice);
            Assert.assertEquals(total, totalPrice);
            driver.close();
            driver.switchTo().window(mainWindowHandle);

            driver.findElement(By.xpath("//button[@data-testid='cypress-headernav-profile']")).click();
            driver.findElement(By.xpath("//div[*='Log out']")).click();
//           driver.findElement(By.xpath("//button[@data-testid='cypress-headernav-profile']")).click();
//           Assert.assertTrue(driver.findElement(By.linkText("Log in")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//span[@class='t1ng71ne dir dir-ltr']")).isDisplayed());


        }finally {
            Thread.sleep(3000);
            driver.quit();
        }
    }
}



