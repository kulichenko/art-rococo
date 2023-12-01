package guru.qa.rococo.data.dao;

import guru.qa.rococo.data.entity.userdata.UserEntity;

public interface UserDataUserDAO {
    int createUserInUserData(UserEntity user);

    void deleteUserByUsernameInUserData(String username);

    void deleteUserFromUserData(UserEntity user);

    UserEntity getUserInUserDataByUsername(String username);

    void addFriendForUser(UserEntity user);
}
