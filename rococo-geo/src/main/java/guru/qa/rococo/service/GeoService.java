package guru.qa.rococo.service;

import guru.qa.rococo.data.GeoRepository;
import guru.qa.rococo.model.GeoJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GeoService {

    private final GeoRepository geoRepository;

    @Autowired
    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public @Nonnull
    List<GeoJson> findAll() {
        return geoRepository.findAll().stream().map(GeoJson::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public @Nonnull
    List<GeoJson> findByIds(@Nonnull List<String> ids) {

        var entities = geoRepository.findByIdIn(ids.stream().map(UUID::fromString).toList());
        if (entities.isEmpty()) {
            throw new RuntimeException("Country was not found by id");
        } else {
            return entities.get().stream().map(GeoJson::fromEntity).collect(Collectors.toList());

        }
    }

}
