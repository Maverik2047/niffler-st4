package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.INVITATION_SEND;
import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

@ExtendWith(UsersQueueExtension.class)
public class UserWithFriendsTest extends BaseWebTest {

    @BeforeEach
    void doLogin(@User(WITH_FRIENDS) UserJson user) {

        Selenide.open("http://127.0.0.1:3000/main");
        loginPage.clickNifflerAuthorizationPage()
                .setUserName(user.username())
                .setPass(user.testData().password())
                .submit();
    }

    @Test
    @DisplayName("Проверка что у пользователя есть друг")
    void friendsTableShouldNotBeEmptyTest(@User(WITH_FRIENDS) UserJson user) {

        headers.clickFriendsBtn();
        friendsPage.checkFriendsTable(user.testData().friendName(), "You are friends");
    }

    @Test
    @DisplayName("Проверка что в таблице Friends есть пользователи с разными статусами")
    void friendsTableShouldHaveDifferentUsersAndStatusesTest(@User(WITH_FRIENDS) UserJson user,
                                                             @User(INVITATION_SEND) UserJson user2) {
        headers.clickAllPeoplesBtn();
        allPeoplePage.checkFriendsTable(user.testData().friendName(), "You are friends");
        allPeoplePage.checkFriendsTable(user2.testData().friendName(), "Pending invitation");
    }
}
