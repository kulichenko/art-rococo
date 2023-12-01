package guru.qa.rococo.data.repository;


import guru.qa.rococo.data.dao.impl.AuthUserDAOHibernate;
import guru.qa.rococo.data.dao.impl.UserDataUserDAOHibernate;

public class UserRepositoryHibernate extends AbstractUserRepository {
    public UserRepositoryHibernate() {
        super(new AuthUserDAOHibernate(), new UserDataUserDAOHibernate());
    }

}
