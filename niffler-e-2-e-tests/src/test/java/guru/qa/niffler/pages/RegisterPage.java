package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage extends BasePage<RegisterPage> {

    private final WelcomePage welcomePage = new WelcomePage();

    private final SelenideElement userNameField = $("input[name='username']"),
            passwordField = $("input[name='password']"),
            submitPasswordField = $("input[name='passwordSubmit']"),
            signUpBtn = $("button[type='submit']");

    @Step("Перейти на страницу регистрации")
    public RegisterPage openRegistrationPage() {
        welcomePage.registerBtnClick();
        return this;
    }

    @Step("Ввести имя пользователя")
    public RegisterPage setUserName(String name) {
        userNameField.setValue(name).click();
        return this;
    }

    @Step("Ввести пароль")
    public RegisterPage setPass(String pass) {
        passwordField.setValue(pass).click();
        return this;
    }

    @Step("Подтвердить пароль")
    public RegisterPage submitPass(String pass) {
        submitPasswordField.setValue(pass).click();
        return this;
    }

    @Step("Зарегистрировать пользователя")
    public RegisterPage signUp() {
        signUpBtn.click();
        return this;
    }
}
