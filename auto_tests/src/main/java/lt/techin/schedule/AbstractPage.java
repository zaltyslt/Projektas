package lt.techin.schedule;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class AbstractPage {
    protected final WebDriver driver;
    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected void scrollToElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.scrollToElement(element).perform();
        } catch (RuntimeException e) {
            //Ignore
        }
    }

}
