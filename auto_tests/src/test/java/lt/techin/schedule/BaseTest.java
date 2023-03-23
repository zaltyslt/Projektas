package lt.techin.schedule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BaseTest {
    static WebDriver driver;

    @BeforeEach
    public void setup() throws InterruptedException {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new EdgeDriver(edgeOptions);
        driver.manage().window().maximize();
       // driver.get("https://schedule-maker-production.up.railway.app/schedule-maker/#/");
        driver.get("http://localhost:3000/schedule-maker#/");
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }
}