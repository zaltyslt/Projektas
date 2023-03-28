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

    @FindBy(xpath ="//*[@id=\"contacts.directEmail\"]" )
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

    @FindBy(css = "#save-button-create-teacher")
    private WebElement clickOnSaveButton;

    @FindBy(css = "#back-button-create-teacher")
    private WebElement clickOnBackButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;


    public TeacherCreateNewPage(WebDriver driver) {
        super(driver);
    }

    public void setFirstName(String firstName) {
        teacherNameInput.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        teacherSurnameInput.sendKeys(lastName);
    }

    public void setPhone(String phone) {
        teacherPhoneInput.sendKeys(phone);
            }
    public void setEmail(String email) {
        teacherEmailInput.sendKeys(email);
    }


    public void setTeamsName(String teamsName) {
        teacherTeamsNameInput.sendKeys(teamsName);
    }

    public void setTeamsEmail(String teamsEmail) {
        teacherTeamsEmailInput.sendKeys(teamsEmail);
    }

    public void setHours(Integer hours) {
        teacherWorkHoursInput.sendKeys(String.valueOf(hours));
    }

    public List<String> getSelectSiftOptions() {
        selectSiftOptions = new SelectOptionElement(selectSift, driver);
        return selectSiftOptions.getOptions();
    }

    public void selectShiftOption(int optionIndex) {
        selectSiftOptions.selectByIndex(optionIndex);
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


}
