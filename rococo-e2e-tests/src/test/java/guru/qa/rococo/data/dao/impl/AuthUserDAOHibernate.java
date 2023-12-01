package guru.qa.rococo.data.dao.impl;

import guru.qa.rococo.data.DataBase;
import guru.qa.rococo.data.dao.AuthUserDAO;
import guru.qa.rococo.data.entity.auth.AuthUserEntity;
import guru.qa.rococo.data.jpa.EntityManagerFactoryProvider;
import guru.qa.rococo.data.jpa.JpaService;

import java.util.UUID;

public class AuthUserDAOHibernate extends JpaService implements AuthUserDAO {
    public AuthUserDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(DataBase.AUTH).createEntityManager());
    }

    @Override
    public UUID createUser(AuthUserEntity user) {
        user.setPassword(pe.encode(user.getPassword()));
        persist(user);
        return user.getId();
    }

    @Override
    public void deleteUser(AuthUserEntity user) {
        remove(user);
    }

    @Override
    public void deleteUserByUsername(String username) {
        AuthUserEntity user = em.createQuery("SELECT u FROM AuthUserEntity u WHERE u.username=:username", AuthUserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
        deleteUser(user);
    }

    @Override
    public AuthUserEntity getUserById(UUID userId) {
        return em.createQuery("SELECT u FROM AuthUserEntity u WHERE u.id=:id", AuthUserEntity.class)
                .setParameter("id", userId)
                .getSingleResult();
    }

    @Override
    public AuthUserEntity getUserByUsername(String username) {
        return em.createQuery("SELECT u FROM AuthUserEntity u WHERE u.username=:username", AuthUserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public AuthUserEntity updateUser(AuthUserEntity user) {
        return merge(user);
    }
}
