package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement mainPage = $("a[href*='redirect']"),
            userNameField = $("input[name='username']"),
            passField = $("input[name='password']"),
            submitButton = $("button[type='submit']");

    @Step("Перейти на страницу авторизации")
    public LoginPage clickNifflerAuthorizationPage() {
        mainPage.click();
        return this;
    }

    @Step("Ввести имя пользователя")
    public LoginPage setUserName(String value) {
        userNameField.setValue(value);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage setPass(String value) {
        passField.setValue(value);
        return this;
    }

    @Step("Нажать подтвердить")
    public LoginPage submit() {
        submitButton.click();
        return this;
    }
}
