package guru.qa.rococo.controller;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService service;

    @GetMapping
    public List<ArtistJson> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ArtistJson findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PatchMapping
    public ArtistJson editArtist(@RequestBody ArtistJson artistJson) {
        return service.editArtist(artistJson);
    }

    @PostMapping
    public ArtistJson createArtist(@RequestBody ArtistJson artistJson) {
        return service.createArtist(artistJson);
    }
}
