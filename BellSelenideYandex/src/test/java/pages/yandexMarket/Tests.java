package pages.yandexMarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.yandex.YandexMarketCatalogPage;
import pages.yandex.YandexPage;

import static com.codeborne.selenide.Selenide.open;

public class Tests {

    @DisplayName("Проверка результатов вывода моделей телефонов")
    @ParameterizedTest
    @CsvSource(value = {
            "https://yandex.ru/, Электроника, Смартфоны, Apple" })
    public void TestYandexMarket(String url, String catalog, String section, String manufacturer){

        open(url, YandexPage.class)
                .goMarket()
                .selectionFromTheCatalog(catalog, section , YandexMarketCatalogPage.class)
                .chooseManufacturer(manufacturer, YandexMarketCatalogPage.class)
                .checkTitleProduct(manufacturer);
    }


}
