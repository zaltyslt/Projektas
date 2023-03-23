package lt.techin.schedule.group;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GroupListPage extends AbstractPage {
    @FindBy(xpath = "#create-new-group")
    private WebElement selectCreatNewGroupButton;

    public GroupListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreatNewGroupButton() {
        selectCreatNewGroupButton.click();
    }
}
