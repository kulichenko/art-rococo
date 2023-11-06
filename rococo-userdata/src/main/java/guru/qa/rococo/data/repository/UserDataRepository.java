package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.entity.UserEntity;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserEntity, UUID> {
    @Nullable
    UserEntity findByUsername(String username);
}
