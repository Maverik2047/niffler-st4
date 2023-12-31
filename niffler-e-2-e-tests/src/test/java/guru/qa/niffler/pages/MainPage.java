package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class MainPage {

    private final SelenideElement spendingTable = $(".spendings-table tbody"),
            deleteCategory = $(byText("Delete selected"));

    @Step("Найти категорию и кликним по ней")
    public MainPage findAndClickSelectedCategory(String value) {
        spendingTable.$$("tr")
                .find(text(value))
                .$("td [type='checkbox']").scrollTo()
                .click();
        return this;
    }

    @Step("Удалить найденную категорию")
    public MainPage deleteCategory() {
        deleteCategory.click();
        return this;
    }

    @Step("Проверить что категория удалена")
    public MainPage checkCategoriesTableSize(int size) {
        spendingTable.$$("tr")
                .shouldHave(size(size));
        return this;
    }
}
