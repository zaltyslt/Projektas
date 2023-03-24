package lt.techin.schedule.subject;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubjectViewPage extends AbstractPage {

    @FindBy (css = "#edit-button-view-subject")
    private WebElement clickEditButton;
    public SubjectViewPage(WebDriver driver) {
        super(driver);
    }

    public void clickEditButton () {
        clickEditButton.click();
    }


}
