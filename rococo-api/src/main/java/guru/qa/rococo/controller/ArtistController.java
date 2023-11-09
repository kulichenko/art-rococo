package guru.qa.rococo.controller;

import guru.qa.rococo.service.ArtistClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ArtistController {

    private final ArtistClient artistClient;

    @Autowired
    public ArtistController(@Qualifier("rest") ArtistClient artistClient) {
        this.artistClient = artistClient;
    }

    @GetMapping("/artist")
    public Page getAllArtists(
            @RequestParam(required = false, defaultValue = "0") String size,
            @RequestParam(required = false, defaultValue = "5") String page) {

        return artistClient.allArtists(page, size);
    }
}
