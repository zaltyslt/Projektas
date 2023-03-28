package lt.techin.schedule;

import lt.techin.schedule.subject.SubjectCreateNewPage;
import lt.techin.schedule.subject.SubjectEditPage;
import lt.techin.schedule.subject.SubjectListPage;
import lt.techin.schedule.subject.SubjectViewPage;
import org.junit.jupiter.api.Test;
import lt.techin.schedule.utils.RandomUtils;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubjectPageTest extends BaseTest {
    HomePage homePage;
    SubjectCreateNewPage subjectCreateNewPage;
    SubjectListPage subjectListPage;
    SubjectViewPage subjectViewPage;
    SubjectEditPage subjectEditPage;

    @Test
    public void createNewSubject() {
        homePage = new HomePage(driver);
        subjectListPage = new SubjectListPage(driver);
        subjectCreateNewPage = new SubjectCreateNewPage(driver);

        homePage.navigateDalykai();
        subjectListPage.selectCreateNewRoomButton();

        WaitUtils.waitPageToLoad(driver);
        String subjectName = "Dalykas" + RandomUtils.randomNumber(6);
        subjectCreateNewPage.setSubjectName(subjectName);
        subjectCreateNewPage.setSubjectDescription("Dalyko " + subjectName + " aprašymas");

        List<String> moduleOptions = subjectCreateNewPage.getSelectModuleOptions();
        subjectCreateNewPage.selectModuleOption(0);

        List<String> roomOptions = subjectCreateNewPage.getSelectRoomOptions();
        subjectCreateNewPage.selectRoomOptions(0);

        subjectCreateNewPage.clickOnSaveButton();

        WebElement alertMessage = subjectCreateNewPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Sėkmingai sukurta.", alertMessage.getText(), "Error while creating room");
        subjectCreateNewPage.clickOnBackButton();

        WaitUtils.waitPageToLoad(driver);

        subjectListPage.setFilterValue(subjectName);
        assertTrue(subjectListPage.getSubjects().contains(subjectName), "Subject " + subjectName + " not found!");
    }

    @Test
    public void editSubject() {
        homePage = new HomePage(driver);
        subjectListPage = new SubjectListPage(driver);
        subjectViewPage = new SubjectViewPage(driver);
        subjectEditPage = new SubjectEditPage(driver);

        homePage.navigateDalykai();
        subjectListPage.getSubjects();
        subjectListPage.selectSubject(0);
        WaitUtils.waitPageToLoad(driver);
        subjectViewPage.clickEditButton();
        WaitUtils.waitPageToLoad(driver);
        String subjectName = "Dalykas" + RandomUtils.randomNumber(6);
        subjectEditPage.setSubjectName(subjectName);
        List<String> moduleOptions = subjectEditPage.getSelectModuleOptions();
        subjectEditPage.selectModuleOption(0);

        List<String> roomOptions = subjectEditPage.getSelectRoomOptions();
        subjectEditPage.selectRoomOptions(0);


        subjectEditPage.clickSaveButton();


        WebElement alertMessage = subjectEditPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Sėkmingai atnaujinta.", alertMessage.getText(), "Error while creating room");
        subjectEditPage.clickBackButton();

        subjectListPage.getSubjects();
        subjectListPage.setFilterValue(subjectName);

        assertTrue(subjectListPage.getSubjects().contains(subjectName), "Subject " + subjectName + " not found!");

    }

    @Test
    public void deleteSubject() {
        homePage = new HomePage(driver);
        subjectListPage = new SubjectListPage(driver);
        subjectViewPage = new SubjectViewPage(driver);
        subjectEditPage = new SubjectEditPage(driver);

        homePage.navigateDalykai();

        String subjectName = subjectListPage.getSubjects().get(0);
        subjectListPage.selectSubject(0);
        WaitUtils.waitPageToLoad(driver);

        subjectViewPage.clickEditButton();
        WaitUtils.waitPageToLoad(driver);

        subjectEditPage.clickDeleteButton();
        WaitUtils.waitPageToLoad(driver);

        subjectListPage.markCheckBox();
        subjectListPage.setFilterValue(subjectName);
        int subjectCount = subjectListPage.getSubjects().size();
        System.out.println("subjectCount = " + subjectCount);

        assertTrue(subjectListPage.getRemovedSubject().contains(subjectName), "Subject " + subjectName + " not found!");

        subjectListPage.clickRestoreRemovedRoomButton(0);
        WaitUtils.waitPageToLoad(driver);
        subjectListPage.setFilterValue(subjectName);

        int expectedSubject = subjectCount+1;
       new WebDriverWait(driver, Duration.ofSeconds(2)).until(driver -> subjectListPage.getSubjects().size()==expectedSubject);

       assertEquals(expectedSubject, subjectListPage.getSubjects().size(), "Invalid active subjects count");
       assertTrue(subjectListPage.getSubjects().contains(subjectName), "Subject " + subjectName + " not found!");
    }


}




