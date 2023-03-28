package lt.techin.schedule;

import lt.techin.schedule.shift.ShiftCreateNewPage;
import lt.techin.schedule.shift.ShiftEdit;
import lt.techin.schedule.shift.ShiftListPage;
import lt.techin.schedule.shift.ShiftView;
import lt.techin.schedule.utils.RandomUtils;
import lt.techin.schedule.utils.WaitUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShiftPageTest extends BaseTest{
    HomePage homePage;
    ShiftCreateNewPage shiftCreate;
    ShiftEdit shiftEdit;
    ShiftListPage listPage;
    ShiftView shiftView;

    @Test
    public void CreateNewShift(){
        homePage = new HomePage(driver);
        listPage = new ShiftListPage(driver);
        shiftCreate = new ShiftCreateNewPage(driver);

        homePage.navigatePamainos();
        listPage.selectCreateNewShiftButton();
        shiftCreate.shiftNameInput();

    String shiftName = RandomUtils.randomString(6);
    shiftCreate.shiftNameInput(shiftName);
    shiftCreate.saveButton();
    WebElement alert = shiftCreate.getAlertMessage();
    assertTrue(alert.isDisplayed(), "Alert not visible");
    assertEquals("Pamaina sėkmingai pridėta.", alert.getText(), "Error while creating shift");
    shiftCreate.backButton();
    WaitUtils.waitPageToLoad(driver);
    listPage.setFilterValue(shiftName);
    assertTrue(listPage.getShifts().contains(shiftName), "shift " + shiftName + " not found!");


    }
    @Test
    public void editShift(){
        homePage = new HomePage(driver);
        listPage = new ShiftListPage(driver);
        shiftView = new ShiftView(driver);
        shiftEdit = new ShiftEdit(driver);

        homePage.navigatePamainos();
        WaitUtils.waitPageToLoad(driver);
        listPage.getShifts();
        listPage.selectShift(0);
        shiftView.clickEditButton();
        WaitUtils.waitPageToLoad(driver);
        String shiftName = RandomUtils.randomString(1);
        shiftEdit.setShiftName(shiftName);
        shiftEdit.clickSaveButton();
        WaitUtils.waitPageToLoad(driver);
        WebElement alertMessage = shiftEdit.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Pamaina sėkmingai pakeista.", alertMessage.getText(), "Error while creating room");
        shiftEdit.clickBackButton();


    }
}
