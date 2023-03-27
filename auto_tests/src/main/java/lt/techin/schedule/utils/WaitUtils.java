package lt.techin.schedule.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class WaitUtils {
    public  static void waitForJs (WebDriver driver){

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(dr -> ((Long) ((JavascriptExecutor) dr)
                .executeScript("return jQuery.active") == 0));
    }
    public static WebElement getVisibleWithWait(WebElement webElement, WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(visibilityOf(webElement));
    }

    public static WebElement getClickableWithWait(WebElement webElement, WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(elementToBeClickable(webElement));
    }

    public static List<WebElement> getVisibleElementsWithWait(By by, WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(visibilityOfAllElementsLocatedBy(by));
    }

    public static WebElement getElementWithWait(By by, WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(presenceOfElementLocated(by));
    }

    public static void waitPageToLoad(WebDriver driver) {
        /*
          Dabartinis sprendimas yra negalutinis.
          Reiketu geresnio budo kaip palaukti kol react uzkraus puslapio duomenis.
         */
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebElement getElementWithWaitNotFail(By by, WebDriver driver) {
        try {
            return new WebDriverWait(driver, Duration.ofMillis(500))
                    .until(presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            return null;
        }
    }
}
