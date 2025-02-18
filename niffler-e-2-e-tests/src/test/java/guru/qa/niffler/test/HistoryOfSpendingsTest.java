package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.db.model.UserEntity;
import guru.qa.niffler.db.repository.UserRepository;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@ExtendWith(UserRepositoryExtension.class)
public class HistoryOfSpendingsTest extends BaseWebTest {

    private UserRepository userRepository;
    private UserAuthEntity userAuth;
    private UserEntity user;

    @BeforeEach
    void createUser() {

        userAuth = new UserAuthEntity();
        userAuth.setUsername("valentin_5");
        userAuth.setPassword("12345");
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);

        user = new UserEntity();
        user.setUsername("valentin_5");
        user.setCurrency(CurrencyValues.RUB);
        userRepository.createInAuth(userAuth);
        userRepository.createInUserdata(user);

        Selenide.open(Config.getInstance().mainPageUrl());
        loginPage.clickNifflerAuthorizationPage()
                .setUserName(userAuth.getUsername())
                .setPass(userAuth.getPassword())
                .submit();
    }

    @AfterEach
    void removeUser() {
        userRepository.deleteInAuthById(userAuth.getId());
        userRepository.deleteInUserdataById(user.getId());
    }

    @Test
    void historyOfSpendingsFiltersTest() {

        $(byText("History of spendings")).scrollTo();
        $(".spendings__buttons").shouldHave(text("Filters:"));
        $(".spendings__buttons").shouldHave(text("Today"));
        $(".spendings__buttons").shouldHave(text("Last week"));
        $(".spendings__buttons").shouldHave(text("Last month"));
        $(".spendings__buttons").shouldHave(text("All time"));
    }

    @Test
    void addNewSpendingTest() {

        $("input[type='number']").setValue("80000");
        $("button[type='submit']").click();
        $(".form__error").shouldHave(text("Category is required"));
    }
}
