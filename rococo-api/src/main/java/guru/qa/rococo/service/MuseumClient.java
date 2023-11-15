package guru.qa.rococo.service;

import guru.qa.rococo.controller.UserController;
import guru.qa.rococo.model.MuseumJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Qualifier("rest")
public class MuseumClient {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final WebClient webClient;
    private final String museumBaseUri;

    @Autowired
    public MuseumClient(WebClient webClient,
                        @Value("${rococo-museum.base-uri}") String museumBaseUri) {
        this.webClient = webClient;
        this.museumBaseUri = museumBaseUri;
    }

    public Page<MuseumJson> allMuseums(PageRequest pageRequest) {
        URI uri = UriComponentsBuilder.fromHttpUrl(museumBaseUri + "/museum").build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(MuseumJson.class)
                .collectList()
                .map(a -> createPage(a, pageRequest))
                .block();
    }

    public MuseumJson findById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(museumBaseUri + "/museum/" + id).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }


    public Page<MuseumJson> findByTitle(String title, PageRequest pageRequest) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", title);
        URI uri = UriComponentsBuilder
                .fromHttpUrl(museumBaseUri + "/museum")
                .queryParams(params)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(MuseumJson.class)
                .collectList()
                .map(a -> createPage(a, pageRequest))
                .block();
    }

    public MuseumJson createMuseum(MuseumJson museumJson) {
        URI uri = UriComponentsBuilder.fromHttpUrl(museumBaseUri + "/museum").build().toUri();
        return webClient.post()
                .uri(uri)
                .body(Mono.just(museumJson), MuseumJson.class)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    public MuseumJson editMuseum(MuseumJson museumToEdit) {
        URI uri = UriComponentsBuilder.fromHttpUrl(museumBaseUri + "/museum").build().toUri();
        return webClient.patch()
                .uri(uri)
                .body(Mono.just(museumToEdit), MuseumJson.class)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    private Page<MuseumJson> createPage(List<MuseumJson> museum, Pageable pageable) {
        int totalElements = museum.size();
        int offset = totalElements > 0 ? (int) pageable.getOffset() : 0;
        int pageSize = pageable.getPageSize();
        int lastIndex;
        if (offset + pageSize < totalElements - 1) {
            lastIndex = offset + pageSize;
        } else {
            lastIndex = totalElements;
        }
        pageable.getPageNumber();
        var content = museum.subList(offset, lastIndex);
        return new PageImpl<>(content, pageable, totalElements);
    }
}
