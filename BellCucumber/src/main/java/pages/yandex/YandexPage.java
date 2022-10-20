package pages.yandex;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
public class YandexPage extends BasePage{

    @Step("Переход на Яндекс Маркет")
    public YandexMarketHomePage goMarket(){

        $(By.xpath("//a[@data-id='market']")).click();
        return page(YandexMarketHomePage.class);
    }

}
