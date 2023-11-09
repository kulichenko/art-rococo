package guru.qa.rococo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Qualifier("rest")
public class ArtistClient {

    private final WebClient webClient;
    private final String artistBaseUri;

    @Autowired
    public ArtistClient(WebClient webClient,
                        @Value("${rococo-artist.base-uri}") String artistBaseUri) {
        this.webClient = webClient;
        this.artistBaseUri = artistBaseUri;
    }

    public Page allArtists(String page, String size) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", page);
        params.add("size", size);
        URI uri = UriComponentsBuilder.fromHttpUrl(artistBaseUri + "/artist").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Page.class).block();
    }
}
