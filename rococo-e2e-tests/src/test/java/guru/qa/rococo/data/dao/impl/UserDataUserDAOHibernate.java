package guru.qa.rococo.data.dao.impl;

import guru.qa.rococo.data.DataBase;
import guru.qa.rococo.data.dao.UserDataUserDAO;
import guru.qa.rococo.data.entity.userdata.UserEntity;
import guru.qa.rococo.data.jpa.EntityManagerFactoryProvider;
import guru.qa.rococo.data.jpa.JpaService;

public class UserDataUserDAOHibernate extends JpaService implements UserDataUserDAO {
    public UserDataUserDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(DataBase.USERDATA).createEntityManager());
    }

    @Override
    public int createUserInUserData(UserEntity user) {
        persist(user);
        return 0;
    }

    @Override
    public void deleteUserByUsernameInUserData(String username) {
        UserEntity
                user = getUserInUserDataByUsername(username);
        deleteUserFromUserData(user);
    }

    @Override
    public void deleteUserFromUserData(UserEntity user) {
        remove(user);
    }

    @Override
    public UserEntity getUserInUserDataByUsername(String username) {
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.username=:username", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void addFriendForUser(UserEntity user) {
        merge(user);
    }
}
