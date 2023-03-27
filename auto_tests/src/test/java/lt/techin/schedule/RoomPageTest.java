package lt.techin.schedule;

import lt.techin.schedule.room.RoomCreateNewPage;
import lt.techin.schedule.room.RoomEditPage;
import lt.techin.schedule.room.RoomListPage;
import lt.techin.schedule.room.RoomViewPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import lt.techin.schedule.utils.RandomUtils;
import lt.techin.schedule.utils.WaitUtils;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoomPageTest extends BaseTest {

    HomePage homePage;
    RoomListPage roomListPage;
    RoomCreateNewPage roomCreateNewPage;
    RoomViewPage roomViewPage;
    RoomEditPage roomEditPage;

    @Test
    public void createNewRoom() {
        homePage = new HomePage(driver);
        roomListPage = new RoomListPage(driver);
        roomCreateNewPage = new RoomCreateNewPage(driver);

        homePage.navigateToRooms();
        roomListPage.getRooms();

        roomListPage.selectCreateNewRoomButton();

        roomCreateNewPage.selectRoomBuilding();
        String roomName = RandomUtils.randomString(6);
        roomCreateNewPage.inputClassRoomName(roomName);
        roomCreateNewPage.inputRoomDescription("Klasėje gali mokintis 50 mokinių. Viso turi 46 kompiuterius.");
        roomCreateNewPage.clickOnSaveButton();

        WebElement alertMessage = roomCreateNewPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Sėkmingai sukurta.", alertMessage.getText(), "Error while creating room");
        roomCreateNewPage.clickOnBackButton();

        roomListPage.setFilterValue(roomName);
        assertTrue(roomListPage.getRooms().contains(roomName), "Room " + roomName + " not found!");
    }

    @Test
    public void editRoom() {
        homePage = new HomePage(driver);
        roomListPage = new RoomListPage(driver);
        roomViewPage = new RoomViewPage(driver);
        roomEditPage = new RoomEditPage(driver);

        homePage.navigateToRooms();
        roomListPage.getRooms();

        roomListPage.selectRoom(0);
        WaitUtils.waitPageToLoad(driver);

        roomViewPage.clickEditButton();
        WaitUtils.waitPageToLoad(driver);

        String roomName = RandomUtils.randomString(6);
        roomEditPage.setClassroomName(roomName);
        roomEditPage.clickSaveButton();

        WebElement alertMessage = roomEditPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Sėkmingai atnaujinote!", alertMessage.getText(), "Error while creating room");
        roomEditPage.clickBackButton();

        roomListPage.setFilterValue(roomName);
        assertTrue(roomListPage.getRooms().contains(roomName), "Room " + roomName + " not found!");
    }

    @Test
    public void deleteRoom() {
        homePage = new HomePage(driver);
        roomListPage = new RoomListPage(driver);
        roomViewPage = new RoomViewPage(driver);
        roomEditPage = new RoomEditPage(driver);

        homePage.navigateToRooms();
        String roomName = roomListPage.getRooms().get(0);
        roomListPage.selectRoom(0);
        WaitUtils.waitPageToLoad(driver);

        roomViewPage.clickEditButton();
        WaitUtils.waitPageToLoad(driver);

        roomEditPage.clickDeleteButton();
        WaitUtils.waitPageToLoad(driver);

        roomListPage.markCheckBox();
        roomListPage.setFilterValue(roomName);
        int roomsCount = roomListPage.getRooms().size();

        assertTrue(roomListPage.getRemovedRooms().contains(roomName), "Room " + roomName + " not found!");

        roomListPage.clickRestoreRemovedRoomButton(0);

        int expectedRooms = roomsCount+1;
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(driver -> roomListPage.getRooms().size()==expectedRooms);

        assertEquals(expectedRooms, roomListPage.getRooms().size(), "Invalid active rooms count");
        assertTrue(roomListPage.getRooms().contains(roomName), "Room " + roomName + " not found!");
    }
}