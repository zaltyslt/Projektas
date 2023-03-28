package lt.techin.schedule;

import lt.techin.schedule.module.ModuleEditPage;
import lt.techin.schedule.module.ModuleListPage;
import lt.techin.schedule.module.ModuleViewPage;
import lt.techin.schedule.program.ProgramCreateNewPage;
import lt.techin.schedule.program.ProgramEditPage;
import lt.techin.schedule.program.ProgramListPage;
import lt.techin.schedule.program.ProgramViewPage;
import lt.techin.schedule.utils.RandomUtils;
import lt.techin.schedule.utils.WaitUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramPageTest extends BaseTest {

    HomePage homePage;
    ProgramListPage programListPage;
    ProgramCreateNewPage programCreateNewPage;
    ProgramViewPage programViewPage;
    ProgramEditPage programEditPage;

    @Test
    public void createNewProgram() {
        homePage = new HomePage(driver);
        programListPage = new ProgramListPage(driver);
        programCreateNewPage = new ProgramCreateNewPage(driver);

        homePage.navigateToProgramos();

        programListPage.clickCreateNewProgramButton();

        String programNumber = RandomUtils.randomNumber(3);
        String programName = "Programa " + programNumber;
        programCreateNewPage.enterProgramName(programName);
        String programDescription = "Programos " + programNumber + " aprašymas";
        programCreateNewPage.enterProgramDescription(programDescription);

        programCreateNewPage.clickAddSubjectButton();
        programCreateNewPage.clickSubjectsDropdownButton();
        programCreateNewPage.clickFirstSubjectButton();
        programCreateNewPage.enterSubjectHours("10");

        programCreateNewPage.clickProgramSaveButton();

        WebElement alertMessage = programCreateNewPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Sėkmingai pridėta!", alertMessage.getText(), "Error while creating program");
    }

    @Test
    public void editProgram() {
        homePage = new HomePage(driver);
        programListPage = new ProgramListPage(driver);
        programViewPage = new ProgramViewPage(driver);
        programEditPage = new ProgramEditPage(driver);

        homePage.navigateToProgramos();
        programListPage.getPrograms();
        programListPage.selectProgram(0);

        programViewPage.scrollToEditButton();
        programViewPage.clickEditButton();

        String programName = RandomUtils.randomNumber(2);
        programEditPage.setProgramName(programName);
        programEditPage.clickSaveButton();

        WebElement alertMessage = programEditPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Sėkmingai atnaujinote!", alertMessage.getText(), "Error while creating program");
    }

    @Test
    public void deleteProgram() {
        homePage = new HomePage(driver);
        programListPage = new ProgramListPage(driver);
        programViewPage = new ProgramViewPage(driver);
        programEditPage = new ProgramEditPage(driver);

        homePage.navigateToProgramos();
        String programName = programListPage.getPrograms().get(0);
        programListPage.selectProgram(0);
        WaitUtils.waitPageToLoad(driver);

        programViewPage.scrollToEditButton();
        WaitUtils.waitPageToLoad(driver);
        programViewPage.clickEditButton();
        WaitUtils.waitPageToLoad(driver);

        programEditPage.clickDeleteButton();
        WaitUtils.waitPageToLoad(driver);

        programListPage.scrollToCheckBox();
        WaitUtils.waitPageToLoad(driver);
        programListPage.markCheckBox();
        WaitUtils.waitPageToLoad(driver);

        programListPage.setFilterValue(programName);

        //error
        int modulesCount = programListPage.getPrograms().size();

        assertTrue(programListPage.getRemovedPrograms().contains(programName), "Program  " + programName + " not found!");

        programListPage.clickRestoreButton();

        int expectedModules = modulesCount+1;
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(driver -> programListPage.getPrograms().size()==expectedModules);

        assertEquals(expectedModules, programListPage.getPrograms().size(), "Invalid active modules count");
        assertTrue(programListPage.getPrograms().contains(programName), "Program " + programName + " not found!");
    }
}
