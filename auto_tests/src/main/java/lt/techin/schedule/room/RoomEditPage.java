package lt.techin.schedule.room;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import utils.WaitUtils;

public class RoomEditPage extends AbstractPage {

    @FindBy(css = "#classroomName")
    private WebElement classroomName;

    @FindBy(css = "#description")
    private WebElement description;

    @FindBy(xpath = "//*/button[text()=\"Išsaugoti\"]")
    private WebElement saveButton;

    // cia kai sukurs ID mygtukui istrinti, lokatariu pakeisti


    @FindBy(xpath = "//*/button[text()=\"Grįžti\"]")
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

