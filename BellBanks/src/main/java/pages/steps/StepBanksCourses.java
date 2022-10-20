package pages.steps;

import io.qameta.allure.Step;
import pages.home_page.AlfaBank;
import pages.home_page.OpenPage;
import pages.home_page.SberBank;
import pages.home_page.VtbBank;

import static pages.constants.Constant.*;

public class StepBanksCourses {
    private AlfaBank alfaBank;
    private OpenPage openPage;
    private SberBank sberBank;
    private VtbBank vtbBank;

    public StepBanksCourses(AlfaBank alfaBank, OpenPage openPage, SberBank sberBank, VtbBank vtbBank) {
        this.alfaBank = alfaBank;
        this.openPage = openPage;
        this.sberBank = sberBank;
        this.vtbBank = vtbBank;
    }

    private String minCourseBankName;
    private String maxCourseBankName;
    private Double minCourse;
    private Double maxCourse;

    @Step("Выгодные курсы обмена, если сумма равна {exchangeAmount}")
    public void course(StepBanksCourses stepBanksCourses, Double exchangeAmount) {
        System.out.println("Самые выгодные курсы при сумме: " + exchangeAmount + " " + USD + " и " + EUR);

        minCourseBankSells(stepBanksCourses, USD);
        maxCourseBankBuys(stepBanksCourses, USD);
        System.out.println(minCourseBankName + " "  + BANK_SELLS + " валюту " + USD + " за " + minCourse);
        System.out.println(maxCourseBankName + " "  + BANK_BUYS + " валюту " + USD + " за " + maxCourse);

        System.out.println();

        minCourseBankSells(stepBanksCourses, EUR);
        maxCourseBankBuys(stepBanksCourses, EUR);
        System.out.println(minCourseBankName + " "  + BANK_SELLS + " валюту " + EUR + " за " + minCourse);
        System.out.println(maxCourseBankName + " "  + BANK_BUYS + " валюту " + EUR + " за " + maxCourse);



    }

    @Step("Вычисление самой низкой цены за курс в банках")
    private void minCourseBankSells(StepBanksCourses stepBanksCourses, String currencyName){
        stepBanksCourses.alfaBank.preActions();
        Double alfaSell = stepBanksCourses.alfaBank.getCourse(currencyName, BANK_SELLS);

        stepBanksCourses.vtbBank.preActions();
        Double vtbSell = stepBanksCourses.vtbBank.getCourse(currencyName, BANK_SELLS);

        stepBanksCourses.sberBank.preActions();
        Double sberSell = stepBanksCourses.sberBank.getCourse(currencyName, BANK_SELLS);

        stepBanksCourses.openPage.preActions();
        Double openSell = stepBanksCourses.openPage.getCourse(currencyName, BANK_SELLS);


        if (alfaSell <= vtbSell && alfaSell <= sberSell && alfaSell <= openSell){
            minCourse = alfaSell;
            minCourseBankName = stepBanksCourses.alfaBank.getBankName();
        } else if (vtbSell <= alfaSell && vtbSell <= sberSell && vtbSell <= openSell){
            minCourse = vtbSell;
            minCourseBankName = stepBanksCourses.vtbBank.getBankName();
        } else if (sberSell <= alfaSell && sberSell <= vtbSell && sberSell <= openSell){
            minCourse = sberSell;
            minCourseBankName = stepBanksCourses.sberBank.getBankName();
        } else if (openSell <= alfaSell && openSell <= vtbSell && openSell <= sberSell){
            minCourse = openSell;
            minCourseBankName = stepBanksCourses.openPage.getBankName();
        }

    }

    @Step("Вычисление самой высокой цены за курс в банках")
    private void maxCourseBankBuys(StepBanksCourses stepBanksCourses, String currencyName){
        stepBanksCourses.alfaBank.preActions();
        Double alfaBuy = stepBanksCourses.alfaBank.getCourse(currencyName, BANK_BUYS);

        stepBanksCourses.vtbBank.preActions();
        Double vtbBuy = stepBanksCourses.vtbBank.getCourse(currencyName, BANK_BUYS);


        stepBanksCourses.sberBank.preActions();
        Double sberBuy = stepBanksCourses.sberBank.getCourse(currencyName, BANK_BUYS);

        stepBanksCourses.openPage.preActions();
        Double openBuy = stepBanksCourses.openPage.getCourse(currencyName, BANK_BUYS);


        if (alfaBuy >= vtbBuy && alfaBuy >= sberBuy && alfaBuy >= openBuy){
            maxCourse = alfaBuy;
            maxCourseBankName = stepBanksCourses.alfaBank.getBankName();
        } else if (vtbBuy >= alfaBuy && vtbBuy >= sberBuy && vtbBuy >= openBuy){
            maxCourse = vtbBuy;
            maxCourseBankName = stepBanksCourses.vtbBank.getBankName();
        } else if (sberBuy >= alfaBuy && sberBuy >= vtbBuy && sberBuy >= openBuy){
            maxCourse = sberBuy;
            maxCourseBankName = stepBanksCourses.sberBank.getBankName();
        } else if (openBuy >= alfaBuy && openBuy >= vtbBuy && openBuy >= sberBuy){
            maxCourse = openBuy;
            maxCourseBankName = stepBanksCourses.openPage.getBankName();
        }

    }


}
