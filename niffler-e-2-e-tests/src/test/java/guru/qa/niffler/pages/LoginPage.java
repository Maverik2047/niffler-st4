package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement mainPage = $("a[href*='redirect']"),
            userNameField = $("input[name='username']"),
            passField = $("input[name='password']"),
            submitButton = $("button[type='submit']");

    @Step("Кликнем по странице авторизации")
    public LoginPage clickMainPage() {
        mainPage.click();
        return this;
    }

    @Step("Введем имя пользователя")
    public LoginPage setUserName(String value) {
        userNameField.setValue(value);
        return this;
    }

    @Step("Введем пароль")
    public LoginPage setPass(String value) {
        passField.setValue(value);
        return this;
    }

    @Step("Нажмем подтвердить")
    public LoginPage submit() {
        submitButton.click();
        return this;
    }
}
