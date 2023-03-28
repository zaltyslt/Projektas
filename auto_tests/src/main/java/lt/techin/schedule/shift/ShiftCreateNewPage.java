package lt.techin.schedule.shift;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ShiftCreateNewPage extends AbstractPage {
    public ShiftCreateNewPage(WebDriver driver) {super(driver);}
    @FindBy (css = "#name")
    private WebElement shiftNameInput;

    @FindBy (css = "#description")
    private WebElement shiftStartSelect;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;
    @FindBy (css = "#save-button-create-shift")
    private WebElement saveButton;
    @FindBy (xpath = "//*[@id=\"root\"]/div/div/div/form/div[3]/div/a/button")
    private WebElement backButton;
    //Pamainos pavadinimas
    public void shiftNameInput(){ shiftNameInput.click();}
    public void shiftNameInput(String shiftName){ shiftNameInput.sendKeys(shiftName);}

    //issaugoti
    public void saveButton(){saveButton.click();}
    //alertas
    public WebElement getAlertMessage() {
        return WaitUtils.getVisibleWithWait(alertMessage, driver);
    }
    //grizti
    public void backButton(){backButton.click();}
}
