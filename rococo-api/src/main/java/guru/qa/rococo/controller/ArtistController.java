package guru.qa.rococo.controller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.ArtistClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/api/artist")
@RestController
public class ArtistController {

    private final ArtistClient artistClient;

    @Autowired
    public ArtistController(@Qualifier("rest") ArtistClient artistClient) {
        this.artistClient = artistClient;
    }

    @GetMapping
    public Page<ArtistJson> getAllArtists(
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return artistClient.allArtists(PageRequest.of(page, size));
    }


    @GetMapping("/{id}")
    public ArtistJson findById(@PathVariable String id) {
        return artistClient.findById(id);
    }

    @PatchMapping
    public ArtistJson editArtist(@RequestBody ArtistJson artist) {

        return artistClient.editArtist(artist);
    }

    @PostMapping
    public ArtistJson createArtist(@RequestBody ArtistJson artistJson) {
        return artistClient.createArtist(artistJson);
    }
}
