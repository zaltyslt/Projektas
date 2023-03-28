package lt.techin.schedule.shift;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShiftListPage extends AbstractPage {

    @FindBy(css = "#create-new-shift")
    private WebElement selectCreateNewShiftButton;

    public ShiftListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreateNewShiftButton() {
        selectCreateNewShiftButton.click();
    }
}
