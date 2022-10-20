package pages.home_page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenPage implements ExchangePage {
    private String site = "https://www.open.ru/";
    private String bankName = "Открытие";

    private WebDriver driverSiteOpen;

    public OpenPage() {
        this.driverSiteOpen = BasePage.getDriver();
    }

    public String getBankName() {
        return bankName;
    }


    private List<Map<String, String>> getExchange() {
        /**
         * @param selectorCountOfLines - Количество строк в таблице
         * @param selectorCountOfElements - Количество элементов в строках
         * */
        String selectorTable = "//div//table";
        String selectorCountOfLines = selectorTable + "//tr";
        String selectorCountOfElements = selectorCountOfLines + "[1]" + "//td";//

        int countOfLines = driverSiteOpen.findElements(By.xpath(selectorCountOfLines)).size();
        int countOfElements = driverSiteOpen.findElements(By.xpath(selectorCountOfElements)).size();

        List<Map<String, String>> listСurrencies = new ArrayList<>();

        for (int tr = 2; tr <= countOfLines; tr++) {
            Map<String, String> mapCurrencies = new HashMap<>();
            for (int td = 1; td <= countOfElements; td++) {
                String selectorKey = "//div//table//tr[1]//td[" + td + "]";
                String selectorValue = "//div//table//tr[" + tr + "]//td[" + td + "]";
                String key = driverSiteOpen.findElement(By.xpath(selectorKey)).getText();
                String value = driverSiteOpen.findElement(By.xpath(selectorValue)).getText();
                if (key.length() == 0) {
                    continue;
                } else {
                    mapCurrencies.put(key, value);
                }
            }
            listСurrencies.add(mapCurrencies);

        }

        return listСurrencies;

    }

    @Override
    public void preActions() {
        try {
            driverSiteOpen.get(site);
        } catch (org.openqa.selenium.TimeoutException ex) {
            driverSiteOpen.navigate().refresh();
        }
    }

    @Override
    public Double getCourse(String currencyName, String currencyType) {
        List<Map<String, String>> mapsCurrency = getExchange();

        String result = null;
        for (int a = 0; a < mapsCurrency.size(); a++) {
            for (String keyName : mapsCurrency.get(a).keySet()) {
                if (mapsCurrency.get(a).get(keyName).equals(currencyName)) {
                    result = mapsCurrency.get(a).get(currencyType);
                }
            }
        }
        Double course = Double.valueOf(result.replace(",", "."));
        return course;
    }
}
