package lt.techin.schedule.subject;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.SelectOptionElement;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SubjectEditPage extends AbstractPage {
    private SelectOptionElement selectModuleOptions;
    private SelectOptionElement selectRoomOptions;
    @FindBy(css = "#name")
    private WebElement inputSubjectName;

    @FindBy(css = "#save-button-edit-subject")
    private WebElement clickSaveButton;
    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    @FindBy(css = "#save-button-edit-subject")
    private WebElement saveButton;

    @FindBy(css = "#back-button-edit-subject")
    private WebElement backButton;

    @FindBy(css = "#delete-button-edit-subject")
    private WebElement deleteSubjectButton;
    @FindBy(css = "#module")
    private WebElement selectModule;
    @FindBy(css = "#room")
    private WebElement selectRoom;



    public SubjectEditPage(WebDriver driver) {
        super(driver);
    }

    public void setSubjectName(String subjectName) {
        inputSubjectName.click();
        inputSubjectName.clear();
        inputSubjectName.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, subjectName);
    }

    public void clickSaveButton() {
        clickSaveButton.click();
    }

    public WebElement getAlertMessage() {
        return WaitUtils.getVisibleWithWait(alertMessage, driver);
    }

    public void clickBackButton() {
        backButton.click();
    }
    public void clickDeleteButton() {
        WaitUtils.getVisibleWithWait(deleteSubjectButton, driver).click();
//        deleteSubjectButton.click();
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
