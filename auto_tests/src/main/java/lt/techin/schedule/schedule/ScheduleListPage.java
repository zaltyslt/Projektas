package lt.techin.schedule.schedule;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ScheduleListPage extends AbstractPage {
    @FindBy(css = "#create-new-schedule")
    private WebElement selectCreateNewScheduleButton;

    public ScheduleListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreateNewScheduleButton() {
        selectCreateNewScheduleButton.click();
    }
}
