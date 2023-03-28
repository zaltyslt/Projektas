package lt.techin.schedule.subject;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.SelectOptionElement;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SubjectCreateNewPage extends AbstractPage {

    private SelectOptionElement selectModuleOptions;
    private SelectOptionElement selectRoomOptions;
    @FindBy(css = "#name")
    private WebElement inputSubjectName;

    @FindBy(css = "#description")
    private WebElement inputSubjectDescription;

    @FindBy(css = "#module")
    private WebElement selectModule;


    @FindBy(css = "#room")
    private WebElement selectRoom;


    @FindBy(css = "#save-button-create-subject")
    private WebElement clickOnSaveButton;

    @FindBy(css = "#back-button-create-subject")
    private WebElement clickOnBackButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    public SubjectCreateNewPage(WebDriver driver) {
        super(driver);
    }

    public void setSubjectName(String subjectName) {
        inputSubjectName.sendKeys(subjectName);
    }

    public void setSubjectDescription(String description) {
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

    public List<String> getSelectModuleOptions() {
        selectModuleOptions = new SelectOptionElement(selectModule, driver);
        return selectModuleOptions.getOptions();
    }

    public void selectModuleOption(int optionIndex) {
        selectModuleOptions.selectByIndex(optionIndex);
    }

    public List<String> getSelectRoomOptions() {
        selectRoomOptions = new SelectOptionElement(selectRoom, driver);
        return selectRoomOptions.getOptions();
    }

    public void selectRoomOptions(int... optionIndex) {
        selectRoomOptions.selectByIndexes(optionIndex);
    }

}



