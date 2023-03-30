package lt.techin.schedule.program;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ProgramCreateNewPage extends AbstractPage {

    @FindBy(css = "#programName")
    private WebElement programNameInput;

    @FindBy(css = "#description")
    private WebElement programDescriptionInput;

    @FindBy(css = "#add-subject-button-create-program")
    private WebElement addSubjectButton;

    @FindBy(css = "#mui-component-select-subjectName")
    private WebElement subjectsDropdownButton;

    @FindBy(css = ".MuiPaper-root ul li")
    private WebElement firstSubjectButton;

    @FindBy(css = "#hours")
    private WebElement hoursInput;

    @FindBy(css = "#save-button-create-program")
    private WebElement programSaveButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    @FindBy(css = "#back-button-create-program")
    private WebElement BackButton;

    public ProgramCreateNewPage(WebDriver driver) {super(driver);}

    public void enterProgramName (String programName) {programNameInput.sendKeys(programName);}

    public void enterProgramDescription (String description) {programDescriptionInput.sendKeys(description);}

    public void clickAddSubjectButton(){addSubjectButton.click();};

    public void clickSubjectsDropdownButton(){subjectsDropdownButton.click();}

    public void clickFirstSubjectButton(){firstSubjectButton.click();}

    public void selectSubjectsDropdownButton(){
        Select select = new Select(subjectsDropdownButton);
        select.selectByIndex(0);
    }
    public void enterSubjectHours (String subjectHours) {hoursInput.sendKeys(subjectHours);}

    public void clickProgramSaveButton () {programSaveButton.click();}

    public WebElement getAlertMessage() {
        return WaitUtils.getVisibleWithWait(alertMessage, driver);
    }

    public void clickBackButton() {
        BackButton.click();
    }


}

