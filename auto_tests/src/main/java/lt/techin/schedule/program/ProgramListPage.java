package lt.techin.schedule.program;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class ProgramListPage extends AbstractPage {

    @FindBy(css = "#create-new-program")
    private WebElement createNewProgramButton;

    @FindBy(css = ".MuiNativeSelect-select")
    WebElement pageDropdownButton;

    @FindBy(css = "tbody.MuiTableBody-root a")
    private List<WebElement> programNameList;

    @FindBy(css = "input.PrivateSwitchBase-input")
    private WebElement checkBox;

    @FindBy(css = "#search-form")
    private WebElement searchInputField;

    @FindBy (xpath= "//tbody//tr[td/button]")
    private List<WebElement> removedProgramsList;

    @FindBy(css = "#restore-button-list-program")
    private WebElement RestoreButton;

    public ProgramListPage(WebDriver driver) {
        super(driver);
    }

    public void clickCreateNewProgramButton () {
        createNewProgramButton.click();
    }
    public void selectPageDropdownButton(){
        Select select = new Select(pageDropdownButton);
        select.selectByIndex(2);
    }

    public List<String> getPrograms() {
        WaitUtils.getElementWithWait(By.cssSelector("tbody.MuiTableBody-root a"), driver);
        return programNameList.stream().map(el -> el.getText()).collect(Collectors.toList());
    }

    public void selectProgram(int programIndex) {
        programNameList.get(programIndex).click();
    }

    public void scrollToCheckBox() {scrollToElement(checkBox);
        //new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(checkBox));
    }

    public void markCheckBox() {
        if (!checkBox.isSelected()) {
            checkBox.click();}
    }

    public void setFilterValue(String programName) {
        searchInputField.sendKeys(programName);
    }

    public List<String> getRemovedPrograms() {
        WaitUtils.getElementWithWait(By.xpath("//tbody//tr[td/button]"), driver);
        return removedProgramsList.stream()
                .map(row -> row.findElement(By.cssSelector("th")))
                .map(el -> el.getText()).collect(Collectors.toList());
    }


    public void clickRestoreButton() {RestoreButton.click();}
}
