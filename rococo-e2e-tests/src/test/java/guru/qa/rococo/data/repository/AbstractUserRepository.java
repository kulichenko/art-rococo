package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.dao.AuthUserDAO;
import guru.qa.rococo.data.dao.UserDataUserDAO;
import guru.qa.rococo.data.entity.auth.AuthUserEntity;
import guru.qa.rococo.data.entity.userdata.UserEntity;

public abstract class AbstractUserRepository implements UserRepository {
    private final AuthUserDAO authUserDAO;
    private final UserDataUserDAO udUserDAO;

    protected AbstractUserRepository(AuthUserDAO authUserDAO, UserDataUserDAO udUserDAO) {
        this.authUserDAO = authUserDAO;
        this.udUserDAO = udUserDAO;
    }

    @Override
    public void createUserForTest(AuthUserEntity user) {
        authUserDAO.createUser(user);
        udUserDAO.createUserInUserData(fromAuthUser(user));
    }

    @Override
    public void removeAfterTest(AuthUserEntity user) {
        UserEntity userInUd = udUserDAO.getUserInUserDataByUsername(user.getUsername());
        udUserDAO.deleteUserFromUserData(userInUd);
        authUserDAO.deleteUser(user);
    }

    @Override
    public AuthUserEntity getTestUserFromAuth(String username) {
        return authUserDAO.getUserByUsername(username);
    }

    @Override
    public UserEntity getTestUserFromUserdata(String username) {
        return udUserDAO.getUserInUserDataByUsername(username);
    }


    @Override
    public void updateUserForTest(AuthUserEntity user) {

    }

    @Override
    public void updateUserForTest(UserEntity user) {

    }

    private UserEntity fromAuthUser(AuthUserEntity user) {
        UserEntity userdataUser = new UserEntity();
        userdataUser.setUsername(user.getUsername());
        return userdataUser;
    }

}