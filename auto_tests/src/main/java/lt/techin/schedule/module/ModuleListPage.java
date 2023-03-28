package lt.techin.schedule.module;

import com.google.common.collect.FluentIterable;
import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.WaitUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleListPage extends AbstractPage {
    @FindBy(css = "#create-new-module")
    WebElement createNewModuleButton;

    @FindBy(css = ".MuiNativeSelect-select")
    WebElement pageDropdownButton;

    @FindBy(css = "tbody.MuiTableBody-root a")
    private List<WebElement> moduleCodeList;

    @FindBy(css = "input.PrivateSwitchBase-input")
    private WebElement checkBox;

    @FindBy(css = "#search-form")
    private WebElement searchInputField;

    @FindBy (xpath= "//tbody//tr[td/button]")
    private List<WebElement> removedModuleList;

    @FindBy(css = "#restore-button-list-module")
    private WebElement RestoreButton;

    public ModuleListPage(WebDriver driver) {
        super(driver);
    }

    public void clickCreateNewModuleButton() {
        createNewModuleButton.click();
    }

    public void selectPageDropdownButton(){
        Select select = new Select(pageDropdownButton);
        select.selectByIndex(2);
    }

    public List<String> getModules() {
        WaitUtils.getElementWithWait(By.cssSelector("tbody.MuiTableBody-root a"), driver);
        return moduleCodeList.stream().map(el -> el.getText()).collect(Collectors.toList());
    }

    public void selectModule(int moduleIndex) {
        moduleCodeList.get(moduleIndex).click();
    }

    public void scrollToCheckBox() {scrollToElement(checkBox);
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(checkBox));}

    public void markCheckBox() {
        if (!checkBox.isSelected()) {
        checkBox.click();}
    }

    public void setFilterValue(String moduleCode) {
        searchInputField.sendKeys(moduleCode);
    }

    public List<String> getRemovedModules() {
        WaitUtils.getElementWithWait(By.xpath("//tbody//tr[td/button]"), driver);
        return removedModuleList.stream()
                .map(row -> row.findElement(By.cssSelector("th")))
                .map(el -> el.getText()).collect(Collectors.toList());
    }


    public void clickRestoreButton() {RestoreButton.click();}
}
