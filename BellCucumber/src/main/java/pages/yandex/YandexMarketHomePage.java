package pages.yandex;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class YandexMarketHomePage extends BasePage{

    @Step("Выберем из каталога раздел из левого столбца: {valueLeft} и товар с правого: {valueRight}")
    public <T extends BasePage> T selectionFromTheCatalog(String valueLeft, String valueRight , Class<T> typeNextPage) {
        /**
         * @param selectorButtonCatalogInMarket - Кликнем по кнопке каталог
         * @param selectorValueLeft - Укажем какой раздел, у нас "Электроника"
         * @param selectorValueLeft - Наведём мышкой на "Электроника"
         * @param selectorButtonYet - Прокликаем кнопки "Ещё"
         * @param selectorValueRight - Выберем столбец справа и дадим ему значение valueRight
         * */

        switchTo().window(1);
        String selectorButtonCatalogInMarket = "//button[@id = 'catalogPopupButton']";
        $(By.xpath(selectorButtonCatalogInMarket)).click();

        String selectorValueLeft = "//li[@class]//span[@class and contains(text(), '" + valueLeft + "')]";
        $(By.xpath(selectorValueLeft)).hover();

        String selectorButtonYet = "//li//span[contains(text(), 'Ещё')]";
        $$(By.xpath(selectorButtonYet))
                .stream()
                .forEach(e -> e.click());

        String selectorValueRight = "//div[@data-tid]//a[@class and contains(text(), '" + valueLeft + "')]" +
                "/..//a[text() = '" + valueRight + "']";
        $(By.xpath(selectorValueRight)).click();


        return typeNextPage.cast(page(typeNextPage));
    }


}
