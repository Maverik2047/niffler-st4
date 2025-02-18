package guru.qa.niffler.pages.component;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.pages.ProfilePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.condition.PhotoCondition.photoFromClasspath;

public class Header extends BaseComponent<Header> {

    public Header() {
        super($(".header"));
    }

    private final SelenideElement avatar = $(".header__avatar");
    private final SelenideElement friendsButton = self.$("a[href*='/friends']");
    private final SelenideElement profileButton = self.$(".header__avatar");


    @Step("Open Profile page")
    public ProfilePage toProfilePage() {
        profileButton.click();
        return new ProfilePage();
    }

    @Step("check avatar")
    public Header checkAvatar(String imageName) {
        avatar.shouldHave(photoFromClasspath(imageName));
        return this;
    }
}
