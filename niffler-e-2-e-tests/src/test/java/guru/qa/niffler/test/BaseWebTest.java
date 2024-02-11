package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.pages.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class})
public abstract class BaseWebTest {

    LoginPage loginPage = new LoginPage();
    Headers headers = new Headers();
    FriendsPage friendsPage = new FriendsPage();
    AllPeoplePage allPeoplePage = new AllPeoplePage();
    MainPage mainPage = new MainPage();

    static {
        Configuration.browserSize = "1980x1024";
        Configuration.browser = "chrome";
    }
}
