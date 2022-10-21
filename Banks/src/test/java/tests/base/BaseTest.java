package tests.base;

import org.openqa.selenium.WebDriver;
import pages.base.BasePage;
import pages.driver.WebDriverManager;

public class BaseTest {

    protected WebDriver driver = WebDriverManager.createDriver();
    protected BasePage basePage = new BasePage(driver);

}
