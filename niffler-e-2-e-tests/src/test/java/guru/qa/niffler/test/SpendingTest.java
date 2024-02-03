package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.LoginPage;
import guru.qa.niffler.pages.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpendingTest extends BaseWebTest {

    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();

    @BeforeEach
    void doLogin() {
        Selenide.open("http://127.0.0.1:3000/main");
        loginPage.clickNifflerAuthorizationPage()
                .setUserName("duck1")
                .setPass("12345")
                .submit();
    }

    @GenerateCategory(
            username = "duck1",
            category = "Обучение"
    )

    @GenerateSpend(
            username = "duck1",
            description = "QA.GURU Advanced 4",
            amount = 72500.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void spendingShouldBeDeletedByButtonDeleteSpendingTest(SpendJson spend) {
        mainPage.findAndClickSelectedCategory(spend.description())
                .deleteCategory()
                .checkCategoriesTableSize(0);
    }
}
