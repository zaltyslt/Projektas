package lt.techin.schedule.room;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import lt.techin.schedule.utils.WaitUtils;

public class RoomEditPage extends AbstractPage {

    @FindBy(css = "#classroomName")
    private WebElement classroomName;

    @FindBy(css = "#description")
    private WebElement description;

    @FindBy(css = "#save-button-edit-room")
    private WebElement saveButton;


    @FindBy(css = "#back-button-edit-room")
    private WebElement backButton;

    @FindBy(css = ".MuiAlert-message")
    private WebElement alertMessage;

    @FindBy(css = "#delete-button-edit-room")
    private WebElement deleteRoomButton;

    public RoomEditPage(WebDriver driver) {
        super(driver);
    }

    public String getClassroomName() {
        return classroomName.getText();
    }

    public void setClassroomName(String name) {
        classroomName.click();
        classroomName.clear();
        classroomName.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, name);
    }

    public void setDescription(String description) {
        this.description.click();
        this.description.clear();
        this.description.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, description);
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    public void clickDeleteButton() {
        deleteRoomButton.click();
    }

    public WebElement getAlertMessage() {
        return WaitUtils.getVisibleWithWait(alertMessage, driver);
    }

    public void clickBackButton() {
        backButton.click();
    }



    }

