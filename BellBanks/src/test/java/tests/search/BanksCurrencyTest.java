package tests.search;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.home_page.AlfaBank;
import pages.home_page.OpenPage;
import pages.home_page.SberBank;
import pages.home_page.VtbBank;
import pages.steps.StepBanksCourses;
import tests.base.BaseTest;

import static pages.constants.Constant.*;

public class BanksCurrencyTest extends BaseTest {


    @Feature("Получение курса, когда сумма обмена не больше 1 USD и EUR")
    @CsvSource(value = {
            "1.0"})
    @ParameterizedTest
    public void courseBanksOne(Double money) {

        AlfaBank alfaBank = new AlfaBank();
        OpenPage openPage = new OpenPage();
        SberBank sberBank = new SberBank(money);
        VtbBank vtbBank = new VtbBank(money);

        StepBanksCourses stepBanksCourses = new StepBanksCourses(alfaBank, openPage, sberBank, vtbBank);
        stepBanksCourses.course(stepBanksCourses, money);

    }


    /* @Feature("Получение курса, когда сумма обмена больше 1000 USD и EUR")
    @CsvSource(value = {
            "1500.0"})
    @ParameterizedTest
    public void courseBanksOneThousand(Double money){

        AlfaBank alfaBank = new AlfaBank();
        OpenPage openPage = new OpenPage();
        SberBank sberBank = new SberBank(money);
        VtbBank vtbBank = new VtbBank(money);

        StepBanksCourses stepBanksCourses = new StepBanksCourses(alfaBank, openPage, sberBank, vtbBank);
        stepBanksCourses.course(stepBanksCourses, money);

    } */


    /*@Feature("Получение курса, когда сумма обмена больше 30000 USD и EUR")
    @CsvSource(value = {
            "35000.0"})
    @ParameterizedTest
    public void courseBanksThirtyThousand(Double money){

        AlfaBank alfaBank = new AlfaBank();
        OpenPage openPage = new OpenPage();
        SberBank sberBank = new SberBank(money);
        VtbBank vtbBank = new VtbBank(money);

        StepBanksCourses stepBanksCourses = new StepBanksCourses(alfaBank, openPage, sberBank, vtbBank);
        stepBanksCourses.course(stepBanksCourses, money);

    } */


    @AfterEach
    public void closeBellTest() {
        driver.quit();
    }


}

