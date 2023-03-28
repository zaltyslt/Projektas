package lt.techin.schedule.room;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class RoomListPage extends AbstractPage {
    @FindBy(css = "#create-new-room")
    private WebElement selectCreateNewRoomButton;

    @FindBy(css = "#search-form")
    private WebElement filterInputField;
    @FindBy(css = "tbody.MuiTableBody-root a")
    private List<WebElement> roomList;

    @FindBy (xpath= "//tbody//tr[td/button]")
    private List<WebElement> removedRoomList;

    @FindBy(css = "input.PrivateSwitchBase-input")
    private WebElement markCheckbox;

    @FindBy(css = "#restore-button-list-room")
    private WebElement clickRestoreButton;


    public RoomListPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getRooms() {
        WaitUtils.getElementWithWaitNotFail(By.cssSelector("tbody.MuiTableBody-root a"), driver);
        return roomList.stream().map(el -> el.getText()).collect(Collectors.toList());
    }

    public List<String> getRemovedRooms() {
        WaitUtils.getElementWithWaitNotFail(By.xpath("//tbody//tr[td/button]"), driver);
        return removedRoomList.stream()
                .map(row -> row.findElement(By.cssSelector("th")))
                .map(el -> el.getText()).collect(Collectors.toList());
    }

    public void selectCreateNewRoomButton() {
        selectCreateNewRoomButton.click();
    }

    public void setFilterValue(String roomName) {
        filterInputField.sendKeys(roomName);
    }

    public WebElement getRoom(int roomIndex) {
        return roomList.get(roomIndex);
    }

    public void selectRoom(int roomIndex) {
        roomList.get(roomIndex).click();
    }

    public void selectRemovedRoom (int removedRoomIndex) {
        roomList.get(removedRoomIndex).click();
    }

    public void markCheckBox() {
        scrollToElement(markCheckbox);
 //       new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(markCheckbox));
        if (!markCheckbox.isSelected()) {
            markCheckbox.click();
        }
    }

    public void clickRestoreButton() {

        clickRestoreButton.click();
    }

    public void clickRestoreRemovedRoomButton(int roomIndex) {
        removedRoomList.get(roomIndex).findElement(By.xpath("//button[text()=\"Atstatyti\"]")).click();
    }
}
