package lt.techin.schedule.shift;

import lt.techin.schedule.AbstractPage;
import lt.techin.schedule.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import javax.xml.xpath.XPath;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftListPage extends AbstractPage {

    @FindBy(css = "#create-new-shift")
    private WebElement selectCreateNewShiftButton;
    @FindBy (css = "#search-form")
    private WebElement filterInputField;
    @FindBy(css = "tbody.MuiTableBody-root a")
    private List<WebElement> shiftList;
    public ShiftListPage(WebDriver driver) {
        super(driver);
    }


    public void selectCreateNewShiftButton() {
        selectCreateNewShiftButton.click();
    }

    public void filterInputField (){filterInputField.click();}
    public void setFilterInputFieldValue (String shiftName){
        filterInputField.sendKeys(shiftName);
    }

    public List<String> getShifts() {
        WaitUtils.getElementWithWait(By.cssSelector("tbody.MuiTableBody-root a"), driver);
        return shiftList.stream().map(el -> el.getText()).collect(Collectors.toList());
    }
    public  void setFilterValue(String shiftName) {
        filterInputField.sendKeys(shiftName);
    }

    public WebElement getShifts(int shiftIndex) {
        return shiftList.get(shiftIndex);
    }
    public void selectShift(int shiftIndex) {
        shiftList.get(shiftIndex).click();
    }
    @FindBy (xpath = "//*[@id='root']/div/div/div/div[3]/label/span[1]/input")
    private WebElement markCheckbox;
    public void markCheck () {
        markCheckbox.isEnabled();
    }
}

