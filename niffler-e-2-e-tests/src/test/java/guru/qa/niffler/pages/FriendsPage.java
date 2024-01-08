package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage {

    private final SelenideElement friendsTable = $(".abstract-table__buttons");

    @Step("Проверка, что в таблице Friends есть друг: {name} со статусом: {state}")
    public FriendsPage checkFriendsTable(String name, String state) {
        friendsTable.$$("tr")
                .find(text(name));
        friendsTable.shouldHave(text(state));
        return this;
    }
}
