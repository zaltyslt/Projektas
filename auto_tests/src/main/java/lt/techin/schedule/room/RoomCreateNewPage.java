package lt.techin.schedule.room;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WaitUtils;

public class RoomCreateNewPage extends AbstractPage {

    @FindBy(css = "#building")
    private WebElement selectBuilding;

    @FindBy(css = "#classroomName")
    private WebElement inputClassRoomName;

    @FindBy(css = "#description")
    private WebElement inputRoomDescription;

    @FindBy(xpath = "//*/button[text()=\"Išsaugoti\"]")
    private WebElement clickOnSaveButton;

    @FindBy(xpath = "//*/button[text()=\"Grįžti\"]")
    private WebElement clickOnBackButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    public RoomCreateNewPage(WebDriver driver) {
        super(driver);
    }

    public void selectRoomBuilding() {
        selectBuilding.getText();
    }

    public void inputClassRoomName(String roomName) {
        inputClassRoomName.sendKeys(roomName);
    }

    public void inputRoomDescription(String description) {
        inputRoomDescription.sendKeys(description);
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
