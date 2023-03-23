package lt.techin.schedule;

import lt.techin.schedule.room.RoomCreateNewPage;
import lt.techin.schedule.room.RoomEditPage;
import lt.techin.schedule.room.RoomListPage;
import lt.techin.schedule.room.RoomViewPage;
import lt.techin.schedule.subject.SubjectCreateNewPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubjectPageTest extends BaseTest {
    HomePage homePage;
    SubjectCreateNewPage subjectCreateNewPage;

    @Test
    public void createNewSubject() {
        homePage = new HomePage(driver);
        subjectCreateNewPage = new SubjectCreateNewPage(driver);





//        roomCreateNewPage.clickOnSaveButton();
//
//        WebElement alertMessage =  roomCreateNewPage.getAlertMessage();
//        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
//        assertEquals("Sėkmingai sukurta.", alertMessage.getText(), "Error while creating room");
//        roomCreateNewPage.clickOnBackButton();
//
//        roomListPage.setFilterValue(roomName);
//        assertTrue(roomListPage.getRooms().contains(roomName), "Room " + roomName + " not found!");
    }






    }

