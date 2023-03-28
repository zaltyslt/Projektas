package lt.techin.schedule.teacher;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TeacherListPage extends AbstractPage {
    @FindBy(css = "#create-new-teacher")
    private WebElement selectCreateNewTeacherButton;

    public TeacherListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreateNewTeacherButton() {
        selectCreateNewTeacherButton.click();
    }
}
