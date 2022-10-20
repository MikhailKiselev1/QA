package pages.driver;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static pages.driver.Configuration.PLATFORM_AND_BROWSER;

public class WebDriverManager {

    public static WebDriver createDriver() {
        WebDriver driver = null;

        switch (PLATFORM_AND_BROWSER) {
            case "windows_chrome":
                System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
                driver = new ChromeDriver();
                break;
            default:
                Assertions.fail("Incorrect name browser or platform " + PLATFORM_AND_BROWSER);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(120l, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        return driver;
    }

}
