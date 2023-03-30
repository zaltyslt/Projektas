package lt.techin.schedule;

import lt.techin.schedule.module.ModuleCreateNewPage;
import lt.techin.schedule.module.ModuleEditPage;
import lt.techin.schedule.module.ModuleListPage;
import lt.techin.schedule.module.ModuleViewPage;
import lt.techin.schedule.room.RoomCreateNewPage;
import lt.techin.schedule.room.RoomEditPage;
import lt.techin.schedule.room.RoomListPage;
import lt.techin.schedule.room.RoomViewPage;
import lt.techin.schedule.utils.RandomUtils;
import lt.techin.schedule.utils.WaitUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModulePageTest extends BaseTest {

    HomePage homePage;
    ModuleListPage moduleListPage;
    ModuleCreateNewPage moduleCreateNewPage;
    ModuleViewPage moduleViewPage;
    ModuleEditPage moduleEditPage;

    @Test
    public void createNewModule() {
        homePage = new HomePage(driver);
        moduleListPage = new ModuleListPage(driver);
        moduleCreateNewPage = new ModuleCreateNewPage(driver);

        homePage.navigateToModules();

        moduleListPage.clickCreateNewModuleButton();

        String moduleCode = RandomUtils.randomNumber(4);
        moduleCreateNewPage.enterModuleCode(moduleCode);
        String moduleName = "Modulis " + RandomUtils.randomNumber(3);
        moduleCreateNewPage.enterModuleName(moduleName);
        moduleCreateNewPage.clickModuleSaveButton();


        WebElement alertMessage = moduleCreateNewPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Modulis sėkmingai sukurtas.", alertMessage.getText(), "Error while creating module");
    }


    @Test
    public void editModule() {
        homePage = new HomePage(driver);
        moduleListPage = new ModuleListPage(driver);
        moduleViewPage = new ModuleViewPage(driver);
        moduleEditPage = new ModuleEditPage(driver);

        homePage.navigateToModules();
        moduleListPage.getModules();
        moduleListPage.selectModule(0);
        WaitUtils.waitPageToLoad(driver);

        moduleViewPage.scrollToEditButton();
        WaitUtils.waitPageToLoad(driver);
        moduleViewPage.clickEditButton();
        WaitUtils.waitPageToLoad(driver);

        String moduleCode = RandomUtils.randomNumber(2);
        moduleEditPage.setModuleCode(moduleCode);
        moduleEditPage.clickSaveButton();

        WebElement alertMessage = moduleEditPage.getAlertMessage();
        assertTrue(alertMessage.isDisplayed(), "Alert not visible");
        assertEquals("Modulis sėkmingai pakeistas.", alertMessage.getText(), "Error while creating module");

    }


    @Test
    public void deleteModule() {
        homePage = new HomePage(driver);
        moduleListPage = new ModuleListPage(driver);
        moduleViewPage = new ModuleViewPage(driver);
        moduleEditPage = new ModuleEditPage(driver);

        homePage.navigateToModules();
        String moduleCode = moduleListPage.getModules().get(0);
        moduleListPage.selectModule(0);
        WaitUtils.waitPageToLoad(driver);

        moduleViewPage.scrollToEditButton();
        WaitUtils.waitPageToLoad(driver);
        moduleViewPage.clickEditButton();
        WaitUtils.waitPageToLoad(driver);

        moduleEditPage.clickDeleteButton();
        WaitUtils.waitPageToLoad(driver);

        moduleListPage.scrollToCheckBox();
        WaitUtils.waitPageToLoad(driver);
        moduleListPage.markCheckBox();
        WaitUtils.waitPageToLoad(driver);

        moduleListPage.setFilterValue(moduleCode);

        assertTrue(moduleListPage.getRemovedModules().contains(moduleCode), "Module  " + moduleCode + " not found!");
    }
}

