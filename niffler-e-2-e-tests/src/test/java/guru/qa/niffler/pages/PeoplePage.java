package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.pages.component.PeopleTable;
import io.qameta.allure.Step;

import static com.codeborne.selenide.ClickOptions.usingJavaScript;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage extends BasePage<PeoplePage> {

    public static final String URL = CFG.frontUrl() + "/people";

    private final SelenideElement tableContainer = $(".people-content");
    private final PeopleTable table = new PeopleTable($(".table"));


    @Step("Send invitation to user: {username}")
    public PeoplePage sendFriendInvitationToUser(String username) {
        SelenideElement friendRow = table.getRowByUsername(username);
        SelenideElement actionsCell = table.getActionsCell(friendRow);
        actionsCell.$(".button-icon_type_add")
                .click(usingJavaScript());
        return this;
    }

    @Step("Check invitation status for user: {username}")
    public PeoplePage checkInvitationSentToUser(String username) {
        SelenideElement friendRow = table.getRowByUsername(username);
        SelenideElement actionsCell = table.getActionsCell(friendRow);
        actionsCell.shouldHave(text("Pending invitation"));
        return this;
    }
}
