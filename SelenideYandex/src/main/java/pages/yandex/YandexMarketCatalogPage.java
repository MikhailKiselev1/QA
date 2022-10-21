package pages.yandex;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;

public class YandexMarketCatalogPage extends BasePage {

    private String grayWaiting = "//div[@class='_2Lvbi _1oZmP']";

    @Step("Выберем производителя: {manufacturer}")
    public <T extends BasePage> T chooseManufacturer(String manufacturer, Class<T> typeNextPage) {
        /**
         * @param selectorManufacturer - Выбрать колонку производитель
         * @param selectorShowAll - В колонке производитель "Показать всё"
         * @param selectorCollapse - "Свернуть" в колонке "Производитель"
         * @param selectorCompany - Выбрать производителя
         * @param buttonShowOn - Клик по "Показывать по"
         * @param selectorCompany - Показывать по 12
         * @param selectorManufacturer + @param selectorShowAll - клик по кнопке показать всё
         * @param selectorManufacturer + @selectorCompany - клик по названию производителя
         * @param selectorLoading - кружок загрузки страницы
         * */

        String selectorManufacturer = "//div[@data-zone-data]//legend[text()='Производитель']";
        String selectorShowAll = "/parent::fieldset//button[text()='Показать всё']";
        String selectorCollapse = "//button[@class and text()='Свернуть']";
        String selectorCompany = "/..//ul//input[not(@disabled) and contains(@name,'" + manufacturer + "')]/parent::label";
        String selectorLoading = "//div[@class='_2cmzC cia-vs']//span[contains(@aria-label, 'Загрузка')]/self::node()";

        Wait().until(ExpectedConditions.elementToBeClickable($($(By.xpath(selectorManufacturer + selectorShowAll)))));

        $(By.xpath(selectorManufacturer + selectorShowAll)).click();
        $(By.xpath(selectorCollapse)).click();
        $(By.xpath(selectorManufacturer + selectorCompany)).click();

        String buttonShowOn = "//button[@type='button']//span[contains(text(), 'Показывать по ')]/../..";
        String buttonShowOnTwelve = "/..//div//button[text()='Показывать по 12']";

        Wait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(grayWaiting)));

        if ($(By.xpath(buttonShowOn)).isDisplayed()) {
            /**
             * Если кнопка показать по 12, 48, показать все отображается на страничке,
             * то кликнем по ней и покажем 12
             * */
            $(By.xpath(buttonShowOn)).click();
            $(By.xpath(buttonShowOn + buttonShowOnTwelve)).click();
        }


        return typeNextPage.cast(page(typeNextPage));
    }

    @Step("Проверяем, что на всех страницах содержится товар с названием: {manufacturer}")
    public YandexMarketCatalogPage checkTitleProduct(String manufacturer) {
        /**
         * @param numberElements - Сколько элементов показывается на странице
         * @param nameFirstElement - Название модели
         * @param numberPage - Номер текущей страницы
         * @param number - Страница по счёту, на какой мы находимся
         * @param numberElements - число элементов на странице, 12, 48 или вся страница открыта
         * @param elementTitle - получим полное название товара
         * */

        String selectorNextPage = "//a[@aria-label = 'Следующая страница']";
        SelenideElement elementNextPage = $(By.xpath(selectorNextPage));

        while (true) {
            Wait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(grayWaiting)));
            int numberElements = $$(By.xpath("//div[@class='cia-vs']//article[@class]")).size();
            for (int a = 1; a <= numberElements; a++) {

                String elementTitle = "//article[" + a + "][@class]//h3//a[@href]//span[@data-tid]";
                String nameFirstElement = $$(By.xpath(elementTitle)).first().getText();

                if (!nameFirstElement.contains(manufacturer)) {
                    Assertions.fail("Элемент № " + a + " не содержит в названии слово " + manufacturer);
                }

            }
            if (elementNextPage.isDisplayed()) {
                /**
                 * Если можем перейти на следующую страничку, кликаем и выполняем цикл заново.
                 * Нет - завершаем цикл
                 * */
                elementNextPage.click();
                continue;
            } else {
                break;
            }

        }
        return this;
    }

}






