package tests.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.yandex.YandexMarketCatalogPage;
import pages.yandex.YandexMarketHomePage;
import pages.yandex.YandexPage;

import static com.codeborne.selenide.Selenide.open;

public class StepDefinition {


    @Given("Открыть yandex.ru")
    public void openBrowser() {
       open("https://yandex.ru/", YandexPage.class);
    }

    @Then("Клик по иконке яндекс маркет и переход на него")
    public void goMarket(){
        new YandexPage().goMarket();
    }

    @When("Выберем из каталога раздел из левого столбца {string} и значение {string}")
    public void selectionFromTheCatalog(String valueLeft, String valueRight){
        new YandexMarketHomePage().selectionFromTheCatalog(valueLeft, valueRight, YandexMarketCatalogPage.class);
    }

    @And("Выберем производителя: {string}")
    public void chooseManufacturer(String manufacturer){
        new YandexMarketCatalogPage().chooseManufacturer(manufacturer, YandexMarketCatalogPage.class);
    }

    @And("Проверяем, что на всех страницах содержится товар с названием: {string}")
    public void checkTitleProduct(String manufacturer){
        new YandexMarketCatalogPage().checkTitleProduct(manufacturer);
    }

    //mvn test -Dcucumber.options="--tags @Run
    //mvn test -Dcucumber.options="src/test/java/tests/features/open.feature"




}
