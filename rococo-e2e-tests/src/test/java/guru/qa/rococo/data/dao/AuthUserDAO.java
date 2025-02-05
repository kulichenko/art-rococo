package guru.qa.rococo.data.dao;

import guru.qa.rococo.data.entity.auth.AuthUserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

//Паттерн DAO - Отвязать реализацию хранения данных от того как программа взаимодействует с этими данными.
public interface AuthUserDAO {
    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    UUID createUser(AuthUserEntity user);

    void deleteUser(AuthUserEntity user);

    void deleteUserByUsername(String username);

    AuthUserEntity getUserById(UUID userId);

    AuthUserEntity getUserByUsername(String username);

    AuthUserEntity updateUser(AuthUserEntity user);
}
