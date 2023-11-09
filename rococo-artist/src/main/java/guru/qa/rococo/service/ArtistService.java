package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.ArtistRepository;
import guru.qa.rococo.model.ArtistJson;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtistService {
    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class);
    private final ArtistRepository repository;

    public ArtistService(ArtistRepository repository) {
        this.repository = repository;
    }

    public @Nonnull
    Page<ArtistEntity> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    public @Nonnull
    Page<ArtistEntity> findById(UUID id, PageRequest pageRequest) {
        return repository.findById(id, pageRequest);
    }

    public @Nonnull
    ArtistJson editArtist(@Nonnull ArtistJson artistJson) {
        Optional<ArtistEntity> artistForEdit = repository.findById(artistJson.id());
        if (artistForEdit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find artist by given id: " + artistJson.id());
        } else {
            ArtistEntity artist = artistForEdit.get();
            artist.setId(artistJson.id());
            artist.setName(artistJson.name());
            artist.setBiography(artistJson.biography());
            artist.setPhoto(artistJson.photo() != null ? artistJson.photo().getBytes(StandardCharsets.UTF_8) : null);
            return ArtistJson.fromEntity(repository.save(artist));
        }
    }

    public @Nonnull
    ArtistJson createArtist(@Nonnull ArtistJson artistJson) {
        ArtistEntity artist = new ArtistEntity();
        artist.setId(artistJson.id());
        artist.setName(artistJson.name());
        artist.setBiography(artistJson.biography());
        artist.setPhoto(artistJson.photo() != null ? artistJson.photo().getBytes(StandardCharsets.UTF_8) : null);
        return ArtistJson.fromEntity(repository.save(artist));
    }
}

