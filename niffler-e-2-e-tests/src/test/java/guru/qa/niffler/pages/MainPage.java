package guru.qa.niffler.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.pages.component.Calendar;
import guru.qa.niffler.pages.component.Select;
import guru.qa.niffler.pages.component.SpendingTable;
import io.qameta.allure.Step;

import java.util.Date;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class MainPage extends BasePage<MainPage> {

    private final SelenideElement spendingTable = $(".spendings-table tbody"),
            deleteCategory = $(byText("Delete selected"));

    public static final String URL = CFG.frontUrl() + "/main";

    private final SelenideElement addSpendingSection = $(".main-content__section-add-spending");
    private final Select categorySelect = new Select(addSpendingSection.$("div.select-wrapper"));
    private final Calendar calendar = new Calendar(addSpendingSection.$(".react-datepicker"));
    private final SelenideElement amountInput = addSpendingSection.$("input[name='amount']");
    private final SelenideElement descriptionInput = addSpendingSection.$("input[name='description']");
    private final SelenideElement submitNewSpendingButton = addSpendingSection.$("button[type='submit']");
    private final SelenideElement errorContainer = addSpendingSection.$(".form__error");

    private final SpendingTable spendTable = new SpendingTable();


    @Step("Найти категорию и кликнуть по ней")
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

    @Step("Проверить траты")
    public SpendingTable getSpendTable() {
        return spendTable;
    }

    public SpendingTable getSpendingTable() {
        spendTable.getSelf().scrollIntoView(true);
        return spendTable;
    }

    @Step("Select new spending category: {0}")
    public MainPage setNewSpendingCategory(String category) {
        Selenide.sleep(1000);
        categorySelect.setValue(category);
        return this;
    }

    @Step("Set new spending amount: {0}")
    public MainPage setNewSpendingAmount(int amount) {
        amountInput.setValue(String.valueOf(amount));
        return this;
    }

    @Step("Set new spending amount: {0}")
    public MainPage setNewSpendingAmount(double amount) {
        amountInput.setValue(String.valueOf(amount));
        return this;
    }

    @Step("Set new spending date: {0}")
    public MainPage setNewSpendingDate(Date date) {
        calendar.selectDateInCalendar(date);
        addSpendingSection.$(byText("Add new spending")).click();
        return this;
    }

    @Step("Set new spending description: {0}")
    public MainPage setNewSpendingDescription(String description) {
        descriptionInput.setValue(description);
        return this;
    }

    @Step("Click submit button to create new spending")
    public MainPage submitNewSpending() {
        submitNewSpendingButton.click();
        return this;
    }

    @Step("Check error: {0} is displayed")
    public MainPage checkError(String error) {
        errorContainer.shouldHave(text(error));
        return this;
    }
}
