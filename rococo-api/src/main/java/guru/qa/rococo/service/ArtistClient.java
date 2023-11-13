package guru.qa.rococo.service;

import guru.qa.rococo.controller.UserController;
import guru.qa.rococo.model.ArtistJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
@Qualifier("rest")
public class ArtistClient {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final WebClient webClient;
    private final String artistBaseUri;

    @Autowired
    public ArtistClient(WebClient webClient,
                        @Value("${rococo-artist.base-uri}") String artistBaseUri) {
        this.webClient = webClient;
        this.artistBaseUri = artistBaseUri;
    }

    public Page<ArtistJson> allArtists(PageRequest pageRequest) {
        URI uri = UriComponentsBuilder.fromHttpUrl(artistBaseUri + "/artist").build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(ArtistJson.class)
                .collectList()
                .map(a -> createPage(a, pageRequest))
                .block();
    }

    public ArtistJson findById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(artistBaseUri + "/artist/" + id).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }

    public ArtistJson editArtist(ArtistJson artistToEdit) {
        URI uri = UriComponentsBuilder.fromHttpUrl(artistBaseUri + "/artist").build().toUri();
        return webClient.patch()
                .uri(uri)
                .body(Mono.just(artistToEdit), ArtistJson.class)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }

    public ArtistJson createArtist(ArtistJson artistToEdit) {
        URI uri = UriComponentsBuilder.fromHttpUrl(artistBaseUri + "/artist").build().toUri();
        return webClient.post()
                .uri(uri)
                .body(Mono.just(artistToEdit), ArtistJson.class)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }

    private Page<ArtistJson> createPage(List<ArtistJson> artists, Pageable pageable) {
        int totalElements = artists.size();
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();
        int lastIndex;
        if (offset + pageSize < totalElements - 1) {
            lastIndex = offset + pageSize;
        } else {
            lastIndex = totalElements;
        }
        pageable.getPageNumber();
        var content = artists.subList(offset, lastIndex);
        return new PageImpl<>(content, pageable, totalElements);
    }
}
