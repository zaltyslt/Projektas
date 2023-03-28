package lt.techin.schedule.shift;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WaitUtils;

public class ShiftEdit extends AbstractPage {
    public ShiftEdit(WebDriver driver) {
        super(driver);
    }

    @FindBy (css = "#name")
    private WebElement shiftName;

    public String getShiftName() {
        return shiftName.getText();
    }

    public void setShiftName(String name) {
        shiftName.click();
        shiftName.clear();
        shiftName.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, name);
}
    @FindBy (css = "#save-button-edit-shift")
    private WebElement saveButton;
    public void clickSaveButton() {
        saveButton.click();
    }
    @FindBy (css = ".MuiAlert-message")
    private WebElement alert;
    public WebElement getAlertMessage() {
        return WaitUtils.getVisibleWithWait(alert, driver);
    }
    @FindBy (css = "#delete-button-edit-shift")
    private WebElement deleteButton;
    public void clickDeleteButton() {
        deleteButton.click();
    }
   @FindBy (css = "#back-button-edit-shift")
    private WebElement backButton;
    public void clickBackButton(){backButton.click();}
    }