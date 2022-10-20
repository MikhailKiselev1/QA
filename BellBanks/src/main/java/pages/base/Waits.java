package pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static pages.constants.Constant.*;


public class Waits {
    public static WebDriverWait wait = new WebDriverWait(BasePage.getDriver(), EXPLICIT_WAIT);
    public static void waitElementPresents(String xpath) {
        Waits.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public static void waitElementIsVisible(WebElement element) {
        new WebDriverWait(BasePage.getDriver(), EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(element));
    }

    public static void invisibilityOfElement(String xpath) {
        new WebDriverWait(BasePage.getDriver(), DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));

        }

    public static void invisibilityOfElementCss(String cssSelector) {
        new WebDriverWait(BasePage.getDriver(), 5)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(cssSelector)));

    }


    public static void waitElementIsClickable(WebElement element) {
        new WebDriverWait(BasePage.getDriver(), DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(element));
    }


    public static void implicitWait() {
        BasePage.getDriver().manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    public static void fullPageWait() {
        BasePage.getDriver().manage().timeouts().pageLoadTimeout(PAGE_WAIT, TimeUnit.SECONDS);
    }


}
