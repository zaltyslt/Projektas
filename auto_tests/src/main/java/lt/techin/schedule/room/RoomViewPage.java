package lt.techin.schedule.room;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RoomViewPage extends AbstractPage {

    @FindBy(xpath = "//*/button[text()=\"Redaguoti\"]")
    private WebElement editButton;

    @FindBy(xpath = "//*/button[text()=\"Grįžti\"]")
    private WebElement backButton;

    public RoomViewPage(WebDriver driver) {
        super(driver);
    }

    public void clickEditButton () {
        editButton.click();
    }

    public void clickBackButton() {
        backButton.click();
    }
}
