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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService service;

    @GetMapping("/artist")
    public Page<ArtistEntity> findAll(@RequestParam(required = false, defaultValue = "5") Integer size,
                                      @RequestParam(required = false, defaultValue = "0") Integer page) {
        return service.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/artist/{id}")
    public Page<ArtistEntity> findById(@PathVariable UUID id,
                                       @RequestParam(required = false, defaultValue = "5") Integer size,
                                       @RequestParam(required = false, defaultValue = "0") Integer page) {
        return service.findById(id, PageRequest.of(page, size));
    }

    @PatchMapping("/artist")
    public ArtistJson editArtist(@RequestBody ArtistJson artistJson) {
        return service.editArtist(artistJson);
    }

    @PostMapping("/artist")
    public ArtistJson createArtist(@RequestBody ArtistJson artistJson) {
        return service.createArtist(artistJson);
    }
}
