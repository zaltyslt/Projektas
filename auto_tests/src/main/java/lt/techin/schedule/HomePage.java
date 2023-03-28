package lt.techin.schedule;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {
    @FindBy(xpath = "//a[text()='Mokytojai']")
    private WebElement navigateMokytojai;

    @FindBy(xpath = "//a[text()='Klasės']")
    private WebElement navigateKlases;

    @FindBy(xpath = "//a[text()='Moduliai']")
    private WebElement navigateModuliai;

    @FindBy(xpath = "//a[text()='Dalykai']")
    private WebElement navigateDalykai;

    @FindBy(xpath = "//a[text()='Pamainos']")
    private WebElement navigatePamainos;

    @FindBy(xpath = "//a[text()='Programos']")
    private WebElement navigateProgramos;

    @FindBy(xpath = "//a[text()='Grupės']")
    private WebElement navigateGrupes;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToMokytojai() {
        navigateMokytojai.click();
        //WaitUtils.waitForJs(driver);
    }

    public void navigateToRooms() {
        navigateKlases.click();
    }

    public void navigateModuliai() {
        navigateModuliai.click();
    }

    public void navigatePamainos() {
        navigatePamainos.click();
    }

    public void navigateDalykai() {
        navigateDalykai.click();
    }

    public void navigateProgramos() {
        navigateProgramos.click();
    }

    public void navigateGrupes() {
        navigateGrupes.click();
    }


}
