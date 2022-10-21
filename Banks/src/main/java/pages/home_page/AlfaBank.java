package pages.home_page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.base.BasePage;
import pages.base.Waits;

import static pages.constants.Constant.*;

public class AlfaBank implements ExchangePage {

    private String site = "https://alfabank.ru/currency/";
    private String bankName = "АльфаБанк";

    private WebDriver driverSiteAlfa;
    private WebElement webElement;

    public AlfaBank() {
        this.driverSiteAlfa = BasePage.getDriver();
    }

    public String getBankName() {
        return bankName;
    }

    /**
     * @param RUBS - на этом сайте RUB = Рубли
     * @param selectorClickOnCurrency - xpath для установления валюты, которую хотим получить
     * @param selectorGetCurrencyStart - xpath начало для получения курса конкретной валюты
     * @param selectorGetCurrencyFinish - selectorGetCurrencyStart + currencyName + selectorGetCurrencyFinish - получим курс валюты
     * @param selectorSetCurrencyStart - xpath для начала установления валюты
     * @param selectorSetCurrencyFinish - selectorSetCurrencyStart + валюта + selectorSetCurrencyFinish - кликнем валюте в столбце слева
     */
    private String RUBS = "Рубли";
    private String selectorGetCurrencyStart = "//div//p[@class and text()='Я получу']/..//table//tbody//tr//td//p[text()='";
    private String selectorGetCurrencyFinish = "']/../..//td//p[contains(text(), 'за')]";

    private String selectorSetCurrencyStart = "//div//p[text()='У меня есть']/..//div//button//p[text()='";
    private String selectorSetCurrencyFinish = "']/..";

    @Override
    public void preActions() {
        try {
            driverSiteAlfa.get(site);
            Waits.implicitWait();
        } catch (org.openqa.selenium.TimeoutException ex) {
            driverSiteAlfa.navigate().refresh();
        }

    }


    @Override
    public Double getCourse(String currencyName, String currencyType) {

        Double course = null;

        if (currencyType.equals("Банк продает")) {
            course = bankSells(currencyName);

        } else if (currencyType.equals("Банк покупает")) {
            course = bankBuys(currencyName);

        } else {
            Assertions.fail("Нет команды под названием " + currencyType);

        }
        return course;
    }


    /**
     * Метод, который выдаст, по какому курсу банк покупает доллары и евро
     */
    private Double bankBuys(String currencyName) {
        /**
         * @param selectorClickOnCurrency - xpath для установления валюты. Если хотим узнать, по какому курсу банк продаёт доллары или евро, то кликаем по значению из @param currencyName
         * Если хотим узнать
         */
        String selectorClickOnCurrency = selectorSetCurrencyStart + currencyName + selectorSetCurrencyFinish;
        clickOnCurrency(selectorClickOnCurrency);

        String xpathGetCurrency = selectorGetCurrencyStart + RUB + selectorGetCurrencyFinish;
        String courseText = driverSiteAlfa.findElement(By.xpath(xpathGetCurrency)).getText();
        Double courseValue = Double.valueOf(courseText.substring(0, courseText.indexOf(" ")).replaceFirst(",", "."));

        return courseValue;

    }

    /**
     * Метод, который выдаст, по какому курсу банк продаёт доллары и евро
     */
    private Double bankSells(String currencyName) {
        /**
         * @param selectorClickOnCurrency - xpath для установления валюты. Если хотим узнать, по какому курсу банк продаёт доллары или евро, то кликаем в таблице по рублям.
         * @param courseText - получим что-то наподобие 105,00 ₽ за 1 $	и после регулярным выражением обрежем до суммы
         */

        String selectorClickOnCurrency = "";
        if (currencyName.equals(USD) || currencyName.equals(EUR)) {
            selectorClickOnCurrency = selectorSetCurrencyStart + RUBS + selectorSetCurrencyFinish;
        }

        clickOnCurrency(selectorClickOnCurrency);

        String xpathGetCurrency = selectorGetCurrencyStart + currencyName + selectorGetCurrencyFinish;
        String courseText = driverSiteAlfa.findElement(By.xpath(xpathGetCurrency)).getText();
        Double courseValue = Double.valueOf(courseText.substring(0, courseText.indexOf(" ")).replaceFirst(",", "."));

        return courseValue;

    }

    /**
     * Кликнем в табличке слева по валюте, какая нас интересует*/
    private void clickOnCurrency(String selectorClickOnCurrency) {
        webElement = driverSiteAlfa.findElement(By.xpath(selectorClickOnCurrency));
        webElement.click();
    }


}
