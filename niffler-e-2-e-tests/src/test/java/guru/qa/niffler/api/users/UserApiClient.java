package guru.qa.niffler.api.users;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

public class UserApiClient extends RestClient {

    private final UserApi userApi;

    public UserApiClient() {
        super(Config.getInstance().userDataUrl());
        userApi = retrofit.create(UserApi.class);
    }

    @DisplayName("Update user info")
    public UserJson updateUserInfo(UserJson user) throws IOException {
        return userApi.updateUserInfo(user).execute().body();
    }
}
