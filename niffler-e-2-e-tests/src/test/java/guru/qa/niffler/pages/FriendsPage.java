package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.pages.component.PeopleTable;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage extends BasePage<FriendsPage> {

    public static final String URL = CFG.frontUrl() + "/friends";

    private final SelenideElement tableContainer = $(".people-content");
    private final PeopleTable table = new PeopleTable($(".table"));

    private final SelenideElement friendsTable = $(".abstract-table__buttons"),
            friendsTableUserName = $(".abstract-table tbody");

    @Step("Проверка, что в таблице Friends есть друг: {name} со статусом: {state}")
    public FriendsPage checkFriendsTable(String name, String state) {
        friendsTable.$$("tr")
                .find(text(name));
        friendsTable.shouldHave(text(state));
        return this;
    }

    @Step("Проверка, что в таблице Friends есть пользователь с запросом в друзья")
    public FriendsPage checkUserFriendRequest(String name) {
        friendsTableUserName.$$("tr")
                .find(text(name))
                .$(".button-icon_type_submit")
                .shouldBe(visible);
        return this;
    }

    @Step("Check that friends count is equal to {expectedCount}")
    public FriendsPage checkExistingFriendsCount(int expectedCount) {
        table.getAllRows().shouldHave(size(expectedCount));
        return this;
    }


    @Step("Delete user from friends: {username}")
    public FriendsPage removeFriend(String username) {
        SelenideElement friendRow = table.getRowByUsername(username);
        SelenideElement actionsCell = table.getActionsCell(friendRow);
        actionsCell.$(".button-icon_type_close")
                .click();
        return this;
    }

    @Step("Accept invitation from user: {username}")
    public FriendsPage acceptFriendInvitationFromUser(String username) {
        SelenideElement friendRow = table.getRowByUsername(username);
        SelenideElement actionsCell = table.getActionsCell(friendRow);
        actionsCell.$(".button-icon_type_submit")
                .click();
        return this;
    }

}
