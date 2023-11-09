package guru.qa.rococo.service;

import guru.qa.rococo.data.entity.UserEntity;
import guru.qa.rococo.data.repository.UserDataRepository;
import guru.qa.rococo.model.UserJson;
import jakarta.annotation.Nonnull;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserDataService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDataService.class);
    private final UserDataRepository userRepository;

    public UserDataService(UserDataRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public @Nonnull
    UserJson getCurrentUser(@Nonnull String username) throws NameNotFoundException {
        return UserJson.fromEntity(getUser(username));
    }

    private static final String DEFAULT_USER_COUNTRY = "Russia";

    @Nonnull
    UserEntity getUser(@Nonnull String username) throws NameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NameNotFoundException(username);
        }
        return Objects.requireNonNull(user);
    }

    @Transactional
    @KafkaListener(topics = "users", groupId = "userdata")
    public void listener(@Payload UserJson user, ConsumerRecord<String, UserJson> cr) {
        LOG.info("### Kafka topic [users] received message: " + user.username());
        LOG.info("### Kafka consumer record: " + cr.toString());
        UserEntity userDataEntity = new UserEntity();
        userDataEntity.setUsername(user.username());
        UserEntity userEntity = userRepository.save(userDataEntity);
        LOG.info(String.format(
                "### User '%s' successfully saved to database with id: %s",
                user.username(),
                userEntity.getId()
        ));
    }

    @Transactional
    public @Nonnull
    UserJson editUser(@Nonnull UserJson user) {
        Optional<UserEntity> userForEdit = userRepository.findById(user.id());
        if (userForEdit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find user by given id: " + user.id());
        } else {
            UserEntity userEntity = userForEdit.get();
            userEntity.setFirstname(user.firstname());
            userEntity.setSurname(user.surname());
            userEntity.setAvatar(user.avatar() != null ? user.avatar().getBytes(StandardCharsets.UTF_8) : null);
            return UserJson.fromEntity(userRepository.save(userEntity));
        }
    }
}
