package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.jupiter.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.FriendsPage;
import guru.qa.niffler.pages.Headers;
import guru.qa.niffler.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.User.UserType.WITH_FRIENDS;

@ExtendWith(UsersQueueExtension.class)
public class FriendsTest {

    LoginPage loginPage = new LoginPage();
    Headers headers = new Headers();
    FriendsPage friendsPage = new FriendsPage();

    @BeforeEach
    void doLogin(@User(WITH_FRIENDS) UserJson user) {
        Selenide.open("http://127.0.0.1:3000/main");
        loginPage.clickNifflerAuthorizationPage()
                .setUserName(user.username())
                .setPass(user.testData().password())
                .submit();
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void friendsTableShouldNotBeEmptyTest(@User(WITH_FRIENDS) UserJson user) throws Exception {
      headers.clickFriendsBtn();
      friendsPage.checkFriendsTable(user.testData().friendName(),"You are friends");
    }

    @Test
    void friendsTableShouldNotBeEmpty1(@User(WITH_FRIENDS) UserJson user) throws Exception {
        Thread.sleep(3000);
    }

    @Test
    void friendsTableShouldNotBeEmpty2(@User(WITH_FRIENDS) UserJson user) throws Exception {
        Thread.sleep(3000);
    }
}
