package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class Headers {

    private final SelenideElement friendsBtn = $("a[href*='friends']");

    @Step("Перейти на страницу Friends")
    public Headers clickFriendsBtn() {
        friendsBtn.click();
        return this;
    }
}
