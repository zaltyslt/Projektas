package lt.techin.schedule.teacher;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.SelectOptionElement;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TeacherCreateNewPage extends AbstractPage {

    private SelectOptionElement selectSiftOptions;
    private SelectOptionElement selectSubjectOptions;
    @FindBy(css = "#fName")
    private WebElement teacherNameInput;

    @FindBy(css = "#lName")
    private WebElement teacherSurnameInput;

    @FindBy(xpath = "//*[@id=\"contacts.phoneNumber\"]")
    private WebElement teacherPhoneInput;

    @FindBy(xpath = "//*[@id=\"contacts.directEmail\"]")
    private WebElement teacherEmailInput;

    @FindBy(xpath = "//*[@id=\"contacts.teamsName\"]")
    private WebElement teacherTeamsNameInput;

    @FindBy(xpath = "//*[@id=\"contacts.teamsEmail\"]")
    private WebElement teacherTeamsEmailInput;

    @FindBy(css = "#workHoursPerWeek")
    private WebElement teacherWorkHoursInput;

    @FindBy(xpath = "//*[@id=\"teacher.selectedShift\"]")
    private WebElement selectSift;


    @FindBy(css = "#add-subject-create-teacher")
    private WebElement selectSubject;

    @FindBy(css = "#subject")
    private WebElement subject;

    @FindBy(css = "#save-button-teacher")
    private WebElement clickOnSaveButton;

    @FindBy(css = "#back-button-teacher")
    private WebElement clickOnBackButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    @FindBy(css = "p.Mui-error")
    private WebElement errorMessage;

    public TeacherCreateNewPage(WebDriver driver) {
        super(driver);
    }

    public void setFirstName(String firstName) {
        teacherNameInput.sendKeys(emptyIfNull(firstName));
    }

    public void setLastName(String lastName) {
        teacherSurnameInput.sendKeys(emptyIfNull(lastName));
    }

    public void setPhone(String phone) {
        teacherPhoneInput.sendKeys(emptyIfNull(phone));
    }

    public void setEmail(String email) {
        teacherEmailInput.sendKeys(emptyIfNull(email));
    }


    public void setTeamsName(String teamsName) {
        teacherTeamsNameInput.sendKeys(emptyIfNull(teamsName));
    }

    public void setTeamsEmail(String teamsEmail) {
        teacherTeamsEmailInput.sendKeys(emptyIfNull(teamsEmail));
    }

    public void setHours(Integer hours) {
        teacherWorkHoursInput.sendKeys(emptyIfNull(hours));
    }

    private String emptyIfNull(Object value) {
        return value == null ? "" : value.toString();
    }

    public List<String> getSelectSiftOptions() {
        selectSiftOptions = new SelectOptionElement(selectSift, driver);
        return selectSiftOptions.getOptions();
    }

    public void selectShiftOption(int optionIndex) {
        selectSiftOptions.selectByIndex(optionIndex);
    }

    public void clickOnSaveButton() {
        scrollToElement(clickOnSaveButton);
        WaitUtils.waitPageToLoad(driver);
        clickOnSaveButton.click();
    }

    public void clickOnBackButton() {
        scrollToElement(clickOnBackButton);
        clickOnBackButton.click();
    }

    public WebElement getAlertMessage() {
        scrollToElement(alertMessage);
        return WaitUtils.getVisibleWithWait(alertMessage, driver);
    }

    public WebElement getErrorMessage() {
        scrollToElement(errorMessage);
        return WaitUtils.getVisibleWithWait(errorMessage, driver);
    }

    public List<String> getSelectSubjectOptions() {
        return selectSubjectOptions.getOptions();
    }

    public void selectSubjectOption(int optionIndex) {
        selectSubjectOptions.selectByIndex(optionIndex);
    }

    public void clickAddSubjectButton() {
        scrollToElement(clickOnSaveButton);
        WaitUtils.waitPageToLoad(driver);
        selectSubject.click();
        selectSubjectOptions = new SelectOptionElement(subject, false, driver);
    }
}



