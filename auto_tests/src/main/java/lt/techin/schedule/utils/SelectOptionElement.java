package lt.techin.schedule.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class SelectOptionElement {

    private final WebElement element;

    private final WebDriver driver;

    private final List<WebElement> selectOptions;

    public SelectOptionElement(WebElement element, WebDriver driver) {
        this.element = element;
        this.driver = driver;
        this.selectOptions = getSelectOptions();
    }

    public List<String> getOptions() {
        return selectOptions.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void selectByIndex(int optionIndex) {
        WebElement option = selectOptions.get(optionIndex);
        option.click();
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(stalenessOf(option));
    }

    private List<WebElement> getSelectOptions() {
        element.click();
        return getSelectOptionElements(element, driver);
    }

    private List<WebElement> getSelectOptionElements(WebElement element, WebDriver driver) {
        String popupName = element.getAttribute("aria-haspopup");
        return WaitUtils.getVisibleElementsWithWait(By.xpath("//ul[@role=\"" + popupName + "\"]/li"), driver);
    }

    public void selectByIndexes(int... optionIndex) {
        IntStream.of(optionIndex).forEach(idx -> selectOptions.get(idx).click());
        WebElement firstOption = selectOptions.get(optionIndex[0]);
        WebElement parent = firstOption.findElement(By.xpath("../../.."));
        parent.click();
    }
}
