package lt.techin.schedule.subject;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SubjectListPage extends AbstractPage {
    @FindBy(css = "#create-new-subject")
    private WebElement selectCreateNewSubjectButton;

    @FindBy(css = "tbody.MuiTableBody-root a")
    private List<WebElement> subjectList;

    public SubjectListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreateNewRoomButton() {
        selectCreateNewSubjectButton.click();
    }
    public List<String> getSubjects() {
        WaitUtils.getElementWithWait(By.cssSelector("tbody.MuiTableBody-root a"), driver);
        return subjectList.stream().map(el -> el.getText()).collect(Collectors.toList());
    }


}
