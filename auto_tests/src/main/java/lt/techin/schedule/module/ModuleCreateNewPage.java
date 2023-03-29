package lt.techin.schedule.module;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ModuleCreateNewPage extends AbstractPage {

    @FindBy(css = "#number")
    private WebElement moduleCodeInput;

    @FindBy(css = "#name")
    private WebElement moduleNameInput;

    @FindBy(css = "#save-button-create-module")
    private WebElement moduleSaveButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    @FindBy(css = "#back-button-create-module")
    private WebElement BackButton;

    public ModuleCreateNewPage(WebDriver driver) {super(driver);}

    public void enterModuleCode (String moduleCode) {moduleCodeInput.sendKeys(moduleCode);}

    public void enterModuleName (String moduleName) {moduleNameInput.sendKeys(moduleName);}

    public void clickModuleSaveButton () {moduleSaveButton.click();}

    public WebElement getAlertMessage() {
        return WaitUtils.getVisibleWithWait(alertMessage, driver);
    }

    public void clickBackButton() {
        BackButton.click();
    }


}
