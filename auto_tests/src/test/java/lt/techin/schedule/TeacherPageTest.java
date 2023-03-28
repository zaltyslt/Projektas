package lt.techin.schedule;

import lt.techin.schedule.teacher.TeacherCreateNewPage;
import lt.techin.schedule.teacher.TeacherListPage;
import lt.techin.schedule.utils.WaitUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

public class TeacherPageTest extends BaseTest {
    HomePage homePage;
    TeacherListPage teacherListPage;
    TeacherCreateNewPage teacherCreateNewPage;

    @ParameterizedTest
    @CsvFileSource(resources = "/teacher_new.csv")
    public void createNewTeacher(String firstName, String lastName, String phone, String email,
                                 String teamsName, String teamsEmail, int hours,
                                 String shift, String subject, boolean success) {

        homePage = new HomePage(driver);
        teacherListPage = new TeacherListPage(driver);
        teacherCreateNewPage = new TeacherCreateNewPage(driver);

        homePage.navigateToMokytojai();
        teacherListPage.selectCreateNewTeacherButton();
        WaitUtils.waitPageToLoad(driver);

        teacherCreateNewPage.setFirstName(firstName);
        teacherCreateNewPage.setLastName(lastName);
        teacherCreateNewPage.setPhone(phone);
        teacherCreateNewPage.setEmail(email);
        teacherCreateNewPage.setTeamsName(teamsName);
        teacherCreateNewPage.setTeamsEmail(teamsEmail);
        teacherCreateNewPage.setHours(40);
        List<String> siftOptions = teacherCreateNewPage.getSelectSiftOptions();
        teacherCreateNewPage.selectShiftOption(1);




        System.out.println(driver.getCurrentUrl());

        /*click();

        if ( success ) {
            Assert.sucess()
        } else {
            Assert.else
        }*/
    }
}