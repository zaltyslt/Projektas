package lt.techin.schedule.subject;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubjectListPage extends AbstractPage {
    @FindBy(xpath = "#create-new-subject")
    private WebElement selectCreatNewSubjectButton;

    public SubjectListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreatNewRoomButton() {
        selectCreatNewSubjectButton.click();
    }
}
