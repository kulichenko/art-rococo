package guru.qa.rococo.data;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public interface GeoRepository extends JpaRepository<GeoEntity, UUID> {

    @Nonnull
    Optional<List<GeoEntity>> findByIdIn(@Nonnull Collection<UUID> ids);

}
