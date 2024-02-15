package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.userdata.Friend;

import javax.annotation.Nonnull;

public record FriendJson(
        @JsonProperty("username")
        String username) {

    @Nonnull
    public Friend toJaxbFriend() {
        Friend jaxbFriend = new Friend();
        jaxbFriend.setUsername(username());
        return jaxbFriend;
    }

    @Nonnull
    public static FriendJson fromJaxb(Friend jaxbFriend) {
        return new FriendJson(jaxbFriend.getUsername());
    }
}