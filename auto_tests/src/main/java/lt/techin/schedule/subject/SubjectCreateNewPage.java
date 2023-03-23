package lt.techin.schedule.subject;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WaitUtils;

public class SubjectCreateNewPage extends AbstractPage {

    @FindBy(css = "#name")
    private WebElement inputSubjectName;

    @FindBy(css = "#description")
    private WebElement inputSubjectDescription;

    @FindBy(css = "#save-button-create-subject")
    private WebElement clickOnSaveButton;
    @FindBy(xpath = "back-button-create-subject")
    private WebElement clickOnBackButton;
    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    @FindBy(css ="data-testid='sentinelStart")
    private WebElement selectModule;

    public SubjectCreateNewPage(WebDriver driver) {
        super(driver);
    }
    public void inputSubjectName(String subjectName) {
        inputSubjectName.sendKeys(subjectName);
    }
    public void inputSubjectDescription(String description) {
        inputSubjectDescription.sendKeys(description);
    }
    public void clickOnSaveButton() {
        clickOnSaveButton.click();
    }
    public void clickOnBackButton() {
        clickOnBackButton.click();
    }
    public WebElement getAlertMessage() {
        return WaitUtils.getVisibleWithWait(alertMessage, driver);
    }

    public void selectModule() {
        selectModule.isSelected();
    }



}



