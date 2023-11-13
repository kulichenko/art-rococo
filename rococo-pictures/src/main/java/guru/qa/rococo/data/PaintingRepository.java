package guru.qa.rococo.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID> {

    List<PaintingEntity> findByTitleContaining(String title);

    List<PaintingEntity> findByArtistId(UUID id);

    Optional<PaintingEntity> findById(UUID id);
}
