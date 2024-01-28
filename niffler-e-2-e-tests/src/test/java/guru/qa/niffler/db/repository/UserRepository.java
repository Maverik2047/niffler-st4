package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.db.model.UserEntity;

import java.util.UUID;

public interface UserRepository {

    UserAuthEntity createInAuth(UserAuthEntity user);

    UserEntity createInUserdata(UserEntity user);

    UserAuthEntity getInAuthById(UUID userId);

    UserEntity getInUserDataById(UUID userId);

    void deleteInAuthById(UUID id);

    void deleteInUserdataById(UUID id);
}
