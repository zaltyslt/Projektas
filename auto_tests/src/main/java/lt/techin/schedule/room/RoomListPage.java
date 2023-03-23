package lt.techin.schedule.room;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RoomListPage extends AbstractPage {
    @FindBy(css = "#create-new-room")
    private WebElement selectCreateNewRoomButton;

    @FindBy(css = "#search-form")
    private WebElement filterInputField;
    @FindBy(css = "tbody.MuiTableBody-root a")
    private List<WebElement> roomList;

    @FindBy (xpath = "//*[@id='root']/div/div/div/div[3]/label/span[1]/input")
    private WebElement markCheckbox;

    public RoomListPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getRooms() {
        WaitUtils.getElementWithWait(By.cssSelector("tbody.MuiTableBody-root a"), driver);
        return roomList.stream().map(el -> el.getText()).collect(Collectors.toList());
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
    public void markCheck () {
        markCheckbox.isEnabled();
    }
}
