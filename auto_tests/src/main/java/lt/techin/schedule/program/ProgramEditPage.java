package lt.techin.schedule.program;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProgramEditPage extends AbstractPage {
    @FindBy(css = "#programName")
    private WebElement programName;

    @FindBy(css = "#description")
    private WebElement programDescription;

    @FindBy(css = "#save-button-edit-program")
    private WebElement saveButton;


    @FindBy(css = "#back-button-edit-program")
    private WebElement backButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    @FindBy(css = "#delete-button-edit-program")
    private WebElement deleteButton;

    public ProgramEditPage(WebDriver driver) {super(driver);}

    public String getProgramName() {
        return programName.getText();
    }

    public void setProgramName(String name){
        programName.click();
        programName.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, name);
    }

    public void setProgramDescription(String description) {
        this.programDescription.click();
        this.programDescription.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, description);
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    public void clickDeleteButton() {
        deleteButton.click();
    }

    public WebElement getAlertMessage() {
        return WaitUtils.getVisibleWithWait(alertMessage, driver);
    }

    public void clickBackButton() {
        backButton.click();
    }
}
