package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.ArtistRepository;
import guru.qa.rococo.model.ArtistJson;
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
public class ArtistService {
    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class);
    private final ArtistRepository repository;

    public ArtistService(ArtistRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public @Nonnull
    List<ArtistJson> findAll() {
        return repository.findAll().stream().map(ArtistJson::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public @Nonnull
    ArtistJson findById(UUID id) {
        var artist = repository.findById(id)
                .stream()
                .map(ArtistJson::fromEntity)
                .findFirst();
        if (artist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find artist by given id: {}" + id);
        } else {
            return artist.get();
        }
    }

    @Transactional(readOnly = true)
    public @Nonnull
    List<ArtistJson> findByName(String name) {
        var artists = repository.findByNameContaining(name)
                .stream()
                .map(ArtistJson::fromEntity)
                .collect(Collectors.toList());
        if (artists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can`t find artists by name: {}" + name);
        } else {
            return artists;
        }
    }

    @Transactional
    public @Nonnull
    ArtistJson editArtist(@Nonnull ArtistJson artistJson) {
        Optional<ArtistEntity> artistForEdit = repository.findById(artistJson.id());
        if (artistForEdit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find artist by given id: {}" + artistJson.id());
        } else {
            ArtistEntity artist = artistForEdit.get();
            artist.setName(artistJson.name());
            artist.setBiography(artistJson.biography());
            artist.setPhoto(artistJson.photo() != null ? artistJson.photo().getBytes(StandardCharsets.UTF_8) : null);
            return ArtistJson.fromEntity(repository.save(artist));
        }
    }

    @Transactional
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

