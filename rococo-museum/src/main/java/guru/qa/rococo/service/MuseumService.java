package guru.qa.rococo.service;

import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.MuseumRepository;
import guru.qa.rococo.model.MuseumJson;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MuseumService {
    private static final Logger LOG = LoggerFactory.getLogger(MuseumService.class);
    private final MuseumRepository repository;

    public MuseumService(MuseumRepository repository) {
        this.repository = repository;
    }


    @Transactional(readOnly = true)
    public @Nonnull
    List<MuseumJson> findAll() {
        return repository.findAll().stream().map(MuseumJson::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public @Nonnull
    List<MuseumJson> findByTitle(String title) {
        List<MuseumEntity> entities = repository.findByTitleContaining(title);
        return entities.stream().map(MuseumJson::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public @Nonnull
    MuseumJson findById(UUID id) {
        Optional<MuseumEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find artist by given id: {}" + id);
        } else {
            return MuseumJson.fromEntity(entity.get());
        }
    }
//
//    @Transactional(readOnly = true)
//    public @Nonnull
//    List<MuseumJson> findByAuthor(UUID id) {
//        List<MuseumEntity> entities = repository.findByArtistId(id);
//        return entities.stream().map(MuseumJson::fromEntity).collect(Collectors.toList());
//    }
}
