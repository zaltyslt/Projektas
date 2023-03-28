package lt.techin.schedule.program;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProgramListPage extends AbstractPage {
    @FindBy(xpath = "#create-new-program")
    private WebElement selectCreateNewProgramButton;

    public ProgramListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreateNewProgramButton() {
        selectCreateNewProgramButton.click();
    }
}
