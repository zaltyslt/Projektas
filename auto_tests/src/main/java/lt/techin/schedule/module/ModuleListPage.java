package lt.techin.schedule.module;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ModuleListPage extends AbstractPage {
    @FindBy(css = "#create-new-module")
    private WebElement selectCreatNewModuleButton;

    public ModuleListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreatNewModuleButton() {
        selectCreatNewModuleButton.click();
    }
}
