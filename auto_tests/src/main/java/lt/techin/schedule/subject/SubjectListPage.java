package lt.techin.schedule.subject;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import lt.techin.schedule.utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SubjectListPage extends AbstractPage {
    @FindBy(css = "#create-new-subject")
    private WebElement selectCreateNewSubjectButton;

    @FindBy(css = "#search-form")
    private WebElement filterInputField;

    @FindBy(css = "tbody.MuiTableBody-root a")
    private List<WebElement> subjectList;

    @FindBy(css = "input.PrivateSwitchBase-input")
    private WebElement markCheckbox;
    @FindBy (xpath= "//tbody//tr[td/button]")
    private List<WebElement> removedSubjectList;

    public SubjectListPage(WebDriver driver) {
        super(driver);
    }

    public void selectCreateNewRoomButton() {
        selectCreateNewSubjectButton.click();
    }
    public List<String> getSubjects() {
        WaitUtils.getElementWithWaitNotFail(By.cssSelector("tbody.MuiTableBody-root a"), driver);
        return subjectList.stream().map(el -> el.getText()).collect(Collectors.toList());
    }
    public List<String> getRemovedSubject() {
        WaitUtils.getElementWithWait(By.xpath("//tbody//tr[td/button]"), driver);
        return removedSubjectList.stream()
                .map(row -> row.findElement(By.cssSelector("th")))
                .map(el -> el.getText()).collect(Collectors.toList());
    }


    public void setFilterValue(String subjectName) {
        filterInputField.sendKeys(subjectName);
    }
    public WebElement getSubject(int subjectIndex) {
        return subjectList.get(subjectIndex);
    }

    public void selectSubject(int subjectIndex) {
        subjectList.get(subjectIndex).click();
    }
    public void markCheckBox() {
        scrollToElement(markCheckbox);
        if (!markCheckbox.isSelected()) {
            markCheckbox.click();
        }
    }
    public void selectRemovedSubject (int removedSubjectIndex) {
        subjectList.get(removedSubjectIndex).click();
    }
    public void clickRestoreRemovedRoomButton(int roomIndex) {
        removedSubjectList.get(roomIndex).findElement(By.xpath("//button[text()=\"Atstatyti\"]")).click();
    }
}
