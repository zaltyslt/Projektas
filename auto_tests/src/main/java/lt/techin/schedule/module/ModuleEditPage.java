package lt.techin.schedule.module;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ModuleEditPage extends AbstractPage {

    @FindBy(css = "input#number")
    private WebElement moduleCode;

    @FindBy(css = "#name")
    private WebElement moduleName;

    @FindBy(css = "#save-button-edit-module")
    private WebElement saveButton;


    @FindBy(css = "#back-button-edit-module")
    private WebElement backButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    @FindBy(css = "#delete-button-edit-module")
    private WebElement deleteButton;
    public ModuleEditPage(WebDriver driver) {super(driver);}

    public String getModuleCode() {
        return moduleCode.getText();
    }

    public void setModuleCode(String code){
        moduleCode.click();
        moduleCode.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, code);
    }

    public void setModuleName(String name) {
        this.moduleName.click();
        this.moduleName.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, name);
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
