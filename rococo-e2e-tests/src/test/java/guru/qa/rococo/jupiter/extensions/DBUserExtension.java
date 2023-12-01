package guru.qa.rococo.jupiter.extensions;

import com.github.javafaker.Faker;
import guru.qa.rococo.data.dao.AuthUserDAO;
import guru.qa.rococo.data.dao.UserDataUserDAO;
import guru.qa.rococo.data.dao.impl.AuthUserDAOHibernate;
import guru.qa.rococo.data.dao.impl.UserDataUserDAOHibernate;
import guru.qa.rococo.data.entity.auth.AuthUserEntity;
import guru.qa.rococo.data.entity.auth.Authority;
import guru.qa.rococo.data.entity.auth.AuthorityEntity;
import guru.qa.rococo.data.entity.userdata.UserEntity;
import guru.qa.rococo.jupiter.annotations.AddUserToDB;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Arrays;
import java.util.stream.Collectors;

@ExtendWith(DaoExtension.class)
public class DBUserExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {

    protected static final String AUTH_USER = "authUser";
    private static final String AUTH_USER_DAO = "authUserDAO";
    private static final String USER_DATA_USER_DAO = "userDataUserDAO";
    public static ExtensionContext.Namespace NAMESPACE_USER = ExtensionContext.Namespace.create(DBUserExtension.class);
    private static final String USER_DATA_USER = "userDataUser";

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AddUserToDB annotation = context.getRequiredTestMethod().getAnnotation(AddUserToDB.class);

        if (annotation != null) {
            AuthUserEntity authUserEntity = new AuthUserEntity();
            UserEntity userDataUserEntity = new UserEntity();
            Faker faker = new Faker();
            if (annotation.username().isEmpty()) {
                authUserEntity.setUsername(faker.name().username());
            } else {
                authUserEntity.setUsername(annotation.username());
            }
            if (annotation.password().isEmpty()) {
                authUserEntity.setPassword(faker.internet().password(3, 12));
            } else {
                authUserEntity.setPassword(annotation.password());
            }
            authUserEntity.setEnabled(true);
            authUserEntity.setAccountNonExpired(true);
            authUserEntity.setAccountNonLocked(true);
            authUserEntity.setCredentialsNonExpired(true);
            authUserEntity.setAuthorities(Arrays.stream(Authority.values())
                    .map(authority -> {
                        var ae = new AuthorityEntity();
                        ae.setAuthority(authority);
                        ae.setUser(authUserEntity);
                        return ae;
                    }).collect(Collectors.toList())
            );
            var pass = authUserEntity.getPassword();
            AuthUserDAO authUserDAO = new AuthUserDAOHibernate();
            UserDataUserDAO userDataUserDAO = new UserDataUserDAOHibernate();
            var userId = authUserDAO.createUser(authUserEntity);
//            authUserEntity.setId(userId);
            userDataUserEntity.setUsername(authUserEntity.getUsername());
            userDataUserDAO.createUserInUserData(userDataUserEntity);
            authUserEntity.setPassword(pass);
            context.getStore(NAMESPACE_USER).put(AUTH_USER, authUserEntity);
            context.getStore(NAMESPACE_USER).put(USER_DATA_USER, userDataUserEntity);
            context.getStore(NAMESPACE_USER).put(AUTH_USER_DAO, authUserDAO);
            context.getStore(NAMESPACE_USER).put(USER_DATA_USER_DAO, userDataUserDAO);
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        var authUserEntity = context.getStore(NAMESPACE_USER).get(AUTH_USER, AuthUserEntity.class);
        var userUserData = context.getStore(NAMESPACE_USER).get(USER_DATA_USER, UserEntity.class);
        AuthUserDAO authUserDAO = (AuthUserDAO) context.getStore(NAMESPACE_USER).get(AUTH_USER_DAO);
        UserDataUserDAO userDataUserDAO = (UserDataUserDAO) context.getStore(NAMESPACE_USER).get(USER_DATA_USER_DAO);
        userDataUserDAO.deleteUserFromUserData(userUserData);
        authUserDAO.deleteUser(authUserEntity);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext
                .getParameter()
                .getType()
                .isAssignableFrom(AuthUserEntity.class);
    }

    @Override
    public AuthUserEntity resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE_USER).get(AUTH_USER, AuthUserEntity.class);
    }
}
