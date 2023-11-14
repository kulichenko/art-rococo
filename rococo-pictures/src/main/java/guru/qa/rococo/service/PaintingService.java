package guru.qa.rococo.service;

import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.data.PaintingRepository;
import guru.qa.rococo.model.PaintingJson;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaintingService {
    private static final Logger LOG = LoggerFactory.getLogger(PaintingService.class);
    private final PaintingRepository repository;

    public PaintingService(PaintingRepository repository) {
        this.repository = repository;
    }


    @Transactional(readOnly = true)
    public @Nonnull
    List<PaintingJson> findAll() {
        return repository.findAll().stream().map(PaintingJson::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public @Nonnull
    List<PaintingJson> findByTitle(String title) {
        List<PaintingEntity> entities = repository.findByTitleContaining(title);
        return entities.stream().map(PaintingJson::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public @Nonnull
    PaintingJson findById(UUID id) {
        Optional<PaintingEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find artist by given id: {}" + id);
        } else {
            return PaintingJson.fromEntity(entity.get());
        }
    }

    @Transactional(readOnly = true)
    public @Nonnull
    List<PaintingJson> findByAuthor(UUID id) {
        List<PaintingEntity> entities = repository.findByArtistId(id);
        return entities.stream().map(PaintingJson::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public @Nonnull
    PaintingJson createPainting(@Nonnull PaintingJson paintingJson) {
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setId(paintingJson.id());
        paintingEntity.setTitle(paintingJson.title());
        paintingEntity.setDescription(paintingJson.description());
        paintingEntity.setContent(paintingJson.content() != null ? paintingJson.content().getBytes(StandardCharsets.UTF_8) : null);
        paintingEntity.setMuseumId(paintingJson.museumId());
        paintingEntity.setArtistId(paintingJson.artistId());
        return PaintingJson.fromEntity(repository.save(paintingEntity));
    }

    @Transactional
    public @Nonnull
    PaintingJson editPainting(@Nonnull PaintingJson paintingJson) {
        var paintingForEdit = repository.findById(paintingJson.id());
        if (paintingForEdit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find Painting by given id: {}" + paintingJson.id());
        } else {
            PaintingEntity paintingEntity = paintingForEdit.get();
            paintingEntity.setTitle(paintingJson.title());
            paintingEntity.setDescription(paintingJson.description());
            paintingEntity.setContent(paintingJson.content() != null ? paintingJson.content().getBytes(StandardCharsets.UTF_8) : null);
            paintingEntity.setMuseumId(paintingJson.museumId());
            paintingEntity.setArtistId(paintingJson.artistId());
            return PaintingJson.fromEntity(repository.save(paintingEntity));
        }
    }
}
