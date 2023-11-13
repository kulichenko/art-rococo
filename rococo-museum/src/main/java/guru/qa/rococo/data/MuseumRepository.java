package guru.qa.rococo.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID> {

    List<MuseumEntity> findByTitleContaining(String title);

    Optional<MuseumEntity> findById(UUID id);
}
