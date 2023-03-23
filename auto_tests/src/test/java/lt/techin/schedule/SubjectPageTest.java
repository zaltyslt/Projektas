package lt.techin.schedule;

import lt.techin.schedule.subject.SubjectCreateNewPage;
import lt.techin.schedule.subject.SubjectListPage;
import org.junit.jupiter.api.Test;
import lt.techin.schedule.utils.RandomUtils;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubjectPageTest extends BaseTest {
    HomePage homePage;
    SubjectCreateNewPage subjectCreateNewPage;
    SubjectListPage subjectListPage;

    @Test
    public void createNewSubject() {
        homePage = new HomePage(driver);
        subjectListPage = new SubjectListPage(driver);
        subjectCreateNewPage = new SubjectCreateNewPage(driver);

        homePage.navigateDalykai();
        subjectListPage.selectCreateNewRoomButton();

        WaitUtils.waitPageToLoad(driver);
        String subjectName = "Dalykas" + RandomUtils.randomString(6);
        subjectCreateNewPage.setSubjectName(subjectName);
        subjectCreateNewPage.setSubjectDescription("Dalyko " + subjectName + " aprašymas");

        List<String> moduleOptions = subjectCreateNewPage.getSelectModuleOptions();
        subjectCreateNewPage.selectModuleOption(0);

        List<String> roomOptions = subjectCreateNewPage.getSelectRoomOptions();
        subjectCreateNewPage.selectRoomOptions(0);

        subjectCreateNewPage.clickOnSaveButton();

        WebElement alertMessage =  subjectCreateNewPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Sėkmingai sukurta.", alertMessage.getText(), "Error while creating room");
        subjectCreateNewPage.clickOnBackButton();

        WaitUtils.waitPageToLoad(driver);

        subjectListPage.setFilterValue(subjectName);
        assertTrue(subjectListPage.getSubjects().contains(subjectName), "Subject " + subjectName + " not found!");
    }






    }

