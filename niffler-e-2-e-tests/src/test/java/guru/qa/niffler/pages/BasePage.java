package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.pages.message.Msg;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage<T extends BasePage> {

    protected static final Config CFG = Config.getInstance();

    protected final SelenideElement toaster = $(".Toastify__toast-body");

    @SuppressWarnings("unchecked")
    @Step("Проверка модального сообщения: {msg}")
    public T checkMessage(Msg msg) {
        toaster.shouldHave(text(msg.getMessage()));
        return (T) this;
    }
}
