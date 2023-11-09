package guru.qa.rococo.data;

import guru.qa.rococo.model.ArtistJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {

    Page<ArtistEntity> findById(UUID id, PageRequest pageRequest);

}
