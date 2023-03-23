package lt.techin.schedule.shift;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShiftListPage extends AbstractPage {

    @FindBy(xpath = "#create-new-shift")
    private WebElement selectCreatNewShiftButton;

    public ShiftListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreatNewShiftButton() {
        selectCreatNewShiftButton.click();
    }
}
