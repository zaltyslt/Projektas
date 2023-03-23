package lt.techin.schedule.schedule;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ScheduleListPage extends AbstractPage {
    @FindBy(xpath = "#create-new-schedule")
    private WebElement selectCreatNewScheduleButton;

    public ScheduleListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreatNewScheduleButton() {
        selectCreatNewScheduleButton.click();
    }
}
