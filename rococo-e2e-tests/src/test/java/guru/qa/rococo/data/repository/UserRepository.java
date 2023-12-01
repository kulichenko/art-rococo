package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.entity.auth.AuthUserEntity;
import guru.qa.rococo.data.entity.userdata.UserEntity;

public interface UserRepository {

    static UserRepository getRepository() {
        return new UserRepositoryHibernate();
    }

    void createUserForTest(AuthUserEntity user);

    AuthUserEntity getTestUserFromAuth(String username);

    UserEntity getTestUserFromUserdata(String username);

    void updateUserForTest(AuthUserEntity user);

    void updateUserForTest(UserEntity user);

    void removeAfterTest(AuthUserEntity user);
}
