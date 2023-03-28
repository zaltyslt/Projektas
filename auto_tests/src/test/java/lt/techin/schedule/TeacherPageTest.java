package lt.techin.schedule;

import lt.techin.schedule.teacher.TeacherCreateNewPage;
import lt.techin.schedule.teacher.TeacherListPage;
import lt.techin.schedule.utils.RandomUtils;
import lt.techin.schedule.utils.WaitUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeacherPageTest extends BaseTest {
    HomePage homePage;
    TeacherListPage teacherListPage;
    TeacherCreateNewPage teacherCreateNewPage;

    @ParameterizedTest
    @CsvFileSource(resources = "/teacher_new.csv")
    public void createNewTeacher(String firstName, String lastName, String phone, String email,
                                 String teamsName, String teamsEmail, Integer hours,
                                 boolean success, String expectedMessage) {

        homePage = new HomePage(driver);
        teacherListPage = new TeacherListPage(driver);
        teacherCreateNewPage = new TeacherCreateNewPage(driver);

        homePage.navigateToMokytojai();
        teacherListPage.selectCreateNewTeacherButton();
        WaitUtils.waitPageToLoad(driver);

        String suffix = "(" + RandomUtils.randomString(4) + ")";
        firstName = append(firstName, suffix);
        lastName = append(lastName, suffix);

        teacherCreateNewPage.setFirstName(firstName);
        teacherCreateNewPage.setLastName(lastName);
        teacherCreateNewPage.setPhone(phone);
        teacherCreateNewPage.setEmail(email);
        teacherCreateNewPage.setTeamsName(teamsName);
        teacherCreateNewPage.setTeamsEmail(teamsEmail);
        teacherCreateNewPage.setHours(hours);
        List<String> siftOptions = teacherCreateNewPage.getSelectSiftOptions();
        teacherCreateNewPage.selectShiftOption(0);
        teacherCreateNewPage.clickAddSubjectButton();
        List<String> subjectOptions = teacherCreateNewPage.getSelectSubjectOptions();
        teacherCreateNewPage.selectSubjectOption(0);

        teacherCreateNewPage.clickOnSaveButton();

        if (success) {
            WebElement alertMessage = teacherCreateNewPage.getAlertMessage();
            assertTrue(alertMessage.isDisplayed(), "Alert not visible");
            assertEquals(expectedMessage, alertMessage.getText(), "Error while creating teacher");

            teacherCreateNewPage.clickOnBackButton();
            WaitUtils.waitPageToLoad(driver);

            //teacherListPage.setFilterValue(firstName);
            //assertTrue(teacherListPage.getTeachers().contains(firstName), "Teacher " + firstName + " not found!");
        } else {
            WebElement errorMessage = teacherCreateNewPage.getErrorMessage();
            assertTrue(errorMessage.isDisplayed(), "Error message is not visible");
            assertEquals(expectedMessage, errorMessage.getText(), "Error while creating teacher");
        }
    }

    private String append(String mainString, String appendedString) {
        return mainString == null ? null : (mainString + appendedString);
    }

}
