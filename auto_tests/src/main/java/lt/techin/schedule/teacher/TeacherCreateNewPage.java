package lt.techin.schedule.teacher;

import lt.techin.schedule.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TeacherCreateNewPage extends AbstractPage {
    @FindBy(css = "#fName")
    private WebElement teacherNameInput;

    @FindBy (css = "#lName-label")
    private WebElement teacherSurnameInput;

    @FindBy (css = "#contacts.phoneNumber-label")
    private WebElement teacherPhoneInput;

    @FindBy (css = "#contacts.teamsName")
    private WebElement teacherTeamsNameInput;

    @FindBy (css = "#contacts.teamsEmail-label")
    private WebElement teacherTeamsEmailInput;

    @FindBy (css = "#workHoursPerWeek")
    private WebElement teacherWorkHoursInput;




        public TeacherCreateNewPage(WebDriver driver) {
        super(driver);
    }

    public void teacherNameInput(String teacherName)
    { teacherNameInput.sendKeys(teacherName);}
    public void teacherSurnameInput(String teacherSurname)
    { teacherSurnameInput.sendKeys(teacherSurname);}
    public void teacherPhoneNumberInput(String phoneNumber)
    { teacherPhoneInput.sendKeys(phoneNumber);}


}
