package guru.qa.rococo.service;

import guru.qa.rococo.data.GeoEntity;
import guru.qa.rococo.data.GeoRepository;
import guru.qa.rococo.model.GeoJson;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GeoService {

    private final GeoRepository geoRepository;

    @Autowired
    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    public Page<GeoEntity> getAllGeo(@PageableDefault PageRequest pageRequest) {
        return geoRepository.findAll(pageRequest);
    }

    @Transactional
    public List<GeoJson> findCountryByIds(@Nonnull List<String> ids) {

        Optional<List<GeoEntity>> entities = geoRepository.findByIdIn(ids.stream().map(UUID::fromString).toList());
        if (entities.isEmpty()) {
            throw new RuntimeException("Country was not found by id");
        } else {
            return entities.get().stream().map(GeoJson::fromEntity).collect(Collectors.toList());

        }
    }

}
