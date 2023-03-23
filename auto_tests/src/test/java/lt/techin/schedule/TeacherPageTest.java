package lt.techin.schedule;

import lt.techin.schedule.teacher.TeacherListPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class TeacherPageTest extends BaseTest {
    HomePage homePage;
    TeacherListPage teacherListPage;

    @ParameterizedTest
    @CsvFileSource(resources = "/teacher_new.csv")
    public void createNewTeacher(String firstName, String lastName, String phone, String email,
                                 String teamsName, String teamsEmail, int hours,
                                 String shift, String subject, boolean success) {
        //Antanas,Antanaitis,861015658,antanas@gmail.com,Antanas,Antanas@vtmc.lt,40,PamainaTest,DalykasTest,true

        homePage = new HomePage(driver);
        teacherListPage = new TeacherListPage(driver);

        homePage.navigateToMokytojai();
        teacherListPage.selectCreateNewTeacherButton();

        System.out.println(driver.getCurrentUrl());

        /*click();

        if ( success ) {
            Assert.sucess()
        } else {
            Assert.else
        }*/
    }
}