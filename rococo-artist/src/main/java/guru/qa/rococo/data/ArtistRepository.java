package guru.qa.rococo.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {

    List<ArtistEntity> findById(UUID id, PageRequest pageRequest);

    List<ArtistEntity> findByNameContaining(String name);
}
