package guru.qa.niffler.api.friends;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.List;

public class FriendsApiClient extends RestClient {

    private final FriendsApi friendsApi;

    public FriendsApiClient() {
        super(Config.getInstance().userDataUrl());
        friendsApi = retrofit.create(FriendsApi.class);
    }

    @DisplayName("Get all friends")
    public List<UserJson> friends(String username, boolean includePending) throws IOException {
        return friendsApi.friends(username, includePending).execute().body();
    }
}
