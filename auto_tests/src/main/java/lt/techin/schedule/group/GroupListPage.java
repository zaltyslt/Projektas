package lt.techin.schedule.group;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GroupListPage extends AbstractPage {
    @FindBy(css = "#create-new-group")
    private WebElement selectCreateNewGroupButton;

    public GroupListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreateNewGroupButton() {
        selectCreateNewGroupButton.click();
    }
}
