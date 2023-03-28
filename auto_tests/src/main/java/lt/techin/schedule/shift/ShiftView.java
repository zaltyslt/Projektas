package lt.techin.schedule.shift;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShiftView extends AbstractPage {
    @FindBy(css = "#edit-button-view-shift")
    private WebElement editButton;

    @FindBy(css = "#back-button-view-shift")
    private WebElement backButton;

    public ShiftView(WebDriver driver) {
        super(driver);
    }


    public void clickEditButton () {
        editButton.click();
    }

    public void clickBackButton() {
        backButton.click();
    }
}

