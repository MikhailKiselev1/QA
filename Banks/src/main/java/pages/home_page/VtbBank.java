package pages.home_page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.base.BasePage;
import pages.base.Waits;

import static pages.constants.Constant.BANK_BUYS;
import static pages.constants.Constant.BANK_SELLS;

public class VtbBank implements ExchangePage {

    private String site = "https://www.vtb.ru/personal/platezhi-i-perevody/obmen-valjuty/";
    private String bankName = "Втб";

    /**
     * @param exchangeAmount - в конструкторе задаётся сумма обмена.
     * На сайте два разных курса обмена валюты, от 30 тысяч и до 30 тысяч
     */
    private WebDriver driverVtbBank;
    private WebElement webElement;
    private Double exchangeAmount;

    public VtbBank(Double exchangeAmount) {
        this.driverVtbBank = BasePage.getDriver();
        this.exchangeAmount = exchangeAmount;
    }

    public String getBankName() {
        return bankName;
    }

    /**
     * @param selector1 and selector2 - Установим, какая нам нужна валюта
     * @param selector2 and selector3 - До 30к или больше 30к
     * @param selectorFromOrTo1 and selectorFromOrTo2 - установим до 30к или от 30к
     * @param selectorFromOrTo2 and selectorSellsOrBuy - Банк покупает или банк продаёт
     */
    private String xpathString = "//div[@class and contains(text(), 'USD')]//span[@class and contains(text(), 'до')]/../../../../..//div[@display='flex']//div//span[@class and text()='Покупаем']/..//div[@direction]//h2[@class]";


    private String spathString = "//div[@class and contains(text(), 'USD')]//span[@class and contains(text(), '')]//span[@class and contains(text(), 'до')]/../../../../..//div[@display='flex']//div//span[@class and text()='Продаем']/..//div[@direction]//h2[@class]";
    private String selectorCurrency1 = "//div[@class and contains(text(), '";
    private String selectorCurrency2 = "')]//span[@class and contains(text(), '";
    private String selectorFromOrTo1 = "')]/../../../../..//div[@display='flex']//div//span[@class and text()='";
    private String selectorFromOrTo2 = "']/..//div[@direction]//h2[@class]";

    private String from = "от";
    private String to = "до";
    private String buy = "Покупаем";
    private String sells = "Продаем";
    private Double thirtyThousand = 30000.0;


    @Override
    public void preActions() {
        String cssSelectorLoading = "#kurs > div > div.layoutstyles__Box-foundation-kit__sc-jkisi6-0.cUVchK.sectionstyles__Container-section__sc-1a06e7j-2 > div > svg";
        driverVtbBank.get(site);
        Waits.fullPageWait();

    }

    /**
     * В методе, собирается xpath, в котором будет сказано, какую валюту нужно взять с сайта,
     * от 30к или до 30к, мы покупаем или продаем
     */


    @Override
    public Double getCourse(String currencyName, String currencyType) {
        Double course = 0.0;

        /**
         * Банк продает и сумма меньше 30к
         * */
        while (true) {
            try {
                if (currencyType.equals(BANK_SELLS) && exchangeAmount < thirtyThousand) {
                    String selectorCourse = selectorCurrency1 + currencyName + selectorCurrency2 + to +
                            selectorFromOrTo1 + sells + selectorFromOrTo2;
                    Waits.waitElementPresents(selectorCourse);
                    webElement = driverVtbBank.findElement(By.xpath(selectorCourse));
                    course = Double.valueOf(webElement.getText().replace(",", "."));
                }

                /**
                 * Банк продает и сумма больше 30к
                 * */
                if (currencyType.equals(BANK_SELLS) && exchangeAmount >= thirtyThousand) {
                    String selectorCourse = selectorCurrency1 + currencyName + selectorCurrency2 + from +
                            selectorFromOrTo1 + sells + selectorFromOrTo2;
                    Waits.waitElementPresents(selectorCourse);
                    webElement = driverVtbBank.findElement(By.xpath(selectorCourse));
                    course = Double.valueOf(webElement.getText().replace(",", "."));
                }

                /**
                 * Банк покупает и сумма меньше 30к
                 * */
                if (currencyType.equals(BANK_BUYS) && exchangeAmount < thirtyThousand) {
                    String selectorCourse = selectorCurrency1 + currencyName + selectorCurrency2 + to +
                            selectorFromOrTo1 + buy + selectorFromOrTo2;
                    Waits.waitElementPresents(selectorCourse);
                    webElement = driverVtbBank.findElement(By.xpath(selectorCourse));
                    course = Double.valueOf(webElement.getText().replace(",", "."));

                }

                /**
                 * Банк покупает и сумма больше 30к
                 * */
                if (currencyType.equals(BANK_BUYS) && exchangeAmount >= thirtyThousand) {
                    String selectorCourse = selectorCurrency1 + currencyName + selectorCurrency2 + from +
                            selectorFromOrTo1 + buy + selectorFromOrTo2;
                    Waits.waitElementPresents(selectorCourse);
                    webElement = driverVtbBank.findElement(By.xpath(selectorCourse));
                    course = Double.valueOf(webElement.getText().replace(",", "."));
                }
                if (course == 0.0) {
                    driverVtbBank.navigate().refresh();
                    continue;
                } else {
                    break;
                }
            } catch (org.openqa.selenium.StaleElementReferenceException ex) {
                ex.getSupportUrl();
                driverVtbBank.navigate().refresh();
                continue;
            }


        }
        return course;

    }


}



