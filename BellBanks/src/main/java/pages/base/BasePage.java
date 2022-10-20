package pages.base;

import org.openqa.selenium.WebDriver;

public class BasePage {

    private static WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String url) {
        driver.get(url);
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
