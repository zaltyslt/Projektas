package lt.techin.schedule.module;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ModuleViewPage extends AbstractPage {

    @FindBy(css = "#edit-button-view-module")
    WebElement editButton;

    @FindBy(css = "#back-button-view-module")
    private WebElement backButton;
    public ModuleViewPage(WebDriver driver) {super(driver);}

    public void scrollToEditButton() {scrollToElement(editButton);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(editButton));}


    public void clickEditButton() {
        editButton.click();
    }

    public void clickBackButton() {
        backButton.click();
    }
}
