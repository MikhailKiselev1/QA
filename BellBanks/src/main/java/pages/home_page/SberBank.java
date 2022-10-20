package pages.home_page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.base.BasePage;
import pages.base.Waits;

import java.util.List;

import static pages.constants.Constant.BANK_BUYS;
import static pages.constants.Constant.BANK_SELLS;

public class SberBank implements ExchangePage {
    private String site = "https://www.sberbank.ru/ru/quotes/currencies";
    private String bankName = "Сбербанк";

    /**
     * @param exchangeAmount - в конструкторе задаётся сумма обмена.
     * На сайте два разных курса обмена валюты, от 30 тысяч и до 30 тысяч
     */

    private WebDriver driverSberbank;
    private WebElement webElement;
    private Double exchangeAmount;


    public SberBank(Double exchangeAmount) {
        this.driverSberbank = BasePage.getDriver();
        this.exchangeAmount = exchangeAmount;
    }

    public String getBankName() {
        return bankName;
    }

    /**
     * @param selectorCurrency - кликнем по полю, которое выдаст нам список валют
     * @param selectorSetCurrency1 - укажем, какая валюта нам будет нужна
     * @param selectorSetCurrency2 - закроем икс пас
     */
    private String selectorCurrency = "//div[@role='button' and @data-test-id='SelectControl_Button-currency']"; //клик по первому чо выбрать
    private String selectorSetCurrency1 = "/..//button[@data-value='";
    private String selectorSetCurrency2 = "']";


    @Override
    public void preActions() {
        driverSberbank.get(site);
        Waits.implicitWait();
    }

    private String sell = "Продать";
    private String buy = "Купить";
    private String number = "Количество";
    private String numberOne = "от 1";
    private String numberOneThousand = "от 1000";
    int positionNumber = 0;
    int positionCurrencyType = 0;

    @Override
    public Double getCourse(String currencyName, String currencyType) {
        /**
         * @param tableCourseCurrency - получаем всю таблицу с сайта
         * @param number1 - добавим этот селектор и + td[positionNumber] и получим столбец с "Количество"
         * @param number2 - закроем селектор "Количество
         * @param sumFrom1 - от 1 доллара или евро
         * @param sumFrom2 - от 1000 долларов или евро
         * @param course1 - получим курс по позиции + positionCurrencyType
         * @param course2 - закроем селектор с получением курса
         * @link setTableCurrency
         * */


        setTableCurrency(currencyName);


        if (currencyType.equals(BANK_BUYS)) {
            currencyType = sell;
        } else if (currencyType.equals(BANK_SELLS)) {
            currencyType = buy;

        } else {
            Assertions.fail("Неверная команда: " + currencyType);
        }

        elementPosition(currencyType);

        String tableCourseCurrency = "//table[@class='rates-table-component']//div[contains(text(), 'Таблица курсов валют')]/..";
        String number1 = "//tbody//tr//td[";
        String number2 = "]";
        String sumFrom1 = "//div[@aria-hidden and text() ='от 1']";
        String sumFrom1000 = "//div[@class and contains(text(), 'от 1000')]";
        String course1 = "/../..//td[";
        String course2 = "]//div[@aria-hidden]";

        String selectorGetCourse = null;
        if (exchangeAmount >= 1000) {
            selectorGetCourse = tableCourseCurrency +
                    number1 + positionNumber + number2 +
                    sumFrom1000 +
                    course1 + positionCurrencyType + course2;
        } else {
            selectorGetCourse = tableCourseCurrency +
                    number1 + positionNumber + number2 +
                    sumFrom1 +
                    course1 + positionCurrencyType + course2;
        }

        Double course = Double.valueOf(driverSberbank.findElement(By.xpath(selectorGetCourse)).getText().replace(",", "."));

        return course;
    }

    private void elementPosition(String currencyType) {
        /**
         * Определим, в какой ячейке по счету находится нужная нам операция и будем подставлять потом позиции в xpath.
         * В цикле
         * 0 Номер. Значение: Валюта
         * 1 Номер. Значение: Количество
         * 2 Номер. Значение: Продать
         * 3 Номер. Значение: Купить
         * */

        String xpathGetSize = "//table[@class='rates-table-component']//div[contains(text(), 'Таблица курсов валют')]/..//thead//tr//th"; //посмотрим, на какой позиции курс "Продать" или "Купить" и потом по этому же расположению определим, в каком месте у нас находится сам курс
        List<WebElement> ratesTableComponent = driverSberbank.findElements(By.xpath(xpathGetSize));


        for (int a = 0; a < ratesTableComponent.size(); a++) {
            String value = ratesTableComponent.get(a).getText();
            if (value.equals(number)) {
                positionNumber = a + 1;
            }
            if (value.equals(currencyType)) {
                positionCurrencyType = a + 1;
            }
        }
    }


    private void setTableCurrency(String currencyName) {
        /**
         * Метод, который выберет на сайте из таблицы нужную нам валюту.
         * Цикл while говорит нам о том, что бывают моменты, когда в тестовом режиме открываешь страницу сбербанка с курсами валют, то
         * может очень долго прогружаться таблица с курсами валют или же просто откроется белая страница без каких-либо данных. Цикл
         * будет обновлять страницу, пока не найдёт нужный селектор.
         * @param selectorLoading - прогрузка таблицы курсов
         * */
        while (true) {
            try {
                String selectorLoading = "//div[@class='kitt-grid kitt-grid_fixed']//div[@class='rates-table-component__td']//div[@class='rates-form__loader']";
                Waits.invisibilityOfElement(selectorLoading);
                if (driverSberbank.findElement(By.xpath(selectorLoading)).isDisplayed() == false) {
                    webElement = driverSberbank.findElement(By.xpath(selectorCurrency));
                    Waits.waitElementIsClickable(webElement);
                    break;
                } else {
                    driverSberbank.navigate().refresh();
                    continue;
                }

            } catch (Exception ex) {
                driverSberbank.navigate().refresh();
                continue;
            } catch (org.opentest4j.AssertionFailedError e) {
                driverSberbank.navigate().refresh();
                continue;
            }
        }

        webElement.click();
        String selectorSetCourse = selectorCurrency + selectorSetCurrency1 + currencyName + selectorSetCurrency2;
        webElement = driverSberbank.findElement(By.xpath(selectorSetCourse));
        Waits.waitElementIsVisible(webElement);
        webElement.click();
    }
}
