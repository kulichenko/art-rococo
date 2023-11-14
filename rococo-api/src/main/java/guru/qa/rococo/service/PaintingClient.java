package guru.qa.rococo.service;

import guru.qa.rococo.controller.UserController;
import guru.qa.rococo.model.PaintingJson;
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

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Qualifier("rest")
public class PaintingClient {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final WebClient webClient;
    private final String picturesBaseUri;

    @Autowired
    public PaintingClient(WebClient webClient,
                          @Value("${rococo-pictures.base-uri}") String picturesBaseUri) {
        this.webClient = webClient;
        this.picturesBaseUri = picturesBaseUri;
    }

    public Page<PaintingJson> allPaintings(PageRequest pageRequest) {
        URI uri = UriComponentsBuilder.fromHttpUrl(picturesBaseUri + "/painting").build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(PaintingJson.class)
                .collectList()
                .map(a -> createPage(a, pageRequest))
                .block();
    }

    public PaintingJson findById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(picturesBaseUri + "/painting/" + id).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }


    public Page<PaintingJson> findByTitle(String title, PageRequest pageRequest) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", title);
        URI uri = UriComponentsBuilder
                .fromHttpUrl(picturesBaseUri + "/painting")
                .queryParams(params)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(PaintingJson.class)
                .collectList()
                .map(a -> createPage(a, pageRequest))
                .block();
    }

    public Page<PaintingJson> findByAuthor(String authorId, PageRequest pageRequest) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(picturesBaseUri + "/painting/author/" + authorId)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(PaintingJson.class)
                .collectList()
                .map(a -> createPage(a, pageRequest))
                .block();
    }

//
//    public PaintingJson editArtist(PaintingJson picturesToEdit) {
//        URI uri = UriComponentsBuilder.fromHttpUrl(picturesBaseUri + "/pictures").build().toUri();
//        return webClient.patch()
//                .uri(uri)
//                .body(Mono.just(picturesToEdit), PaintingJson.class)
//                .retrieve()
//                .bodyToMono(PaintingJson.class)
//                .block();
//    }
//
//    public PaintingJson createArtist(PaintingJson picturesToEdit) {
//        URI uri = UriComponentsBuilder.fromHttpUrl(picturesBaseUri + "/pictures").build().toUri();
//        return webClient.post()
//                .uri(uri)
//                .body(Mono.just(picturesToEdit), PaintingJson.class)
//                .retrieve()
//                .bodyToMono(PaintingJson.class)
//                .block();
//    }

    private Page<PaintingJson> createPage(List<PaintingJson> pictures, Pageable pageable) {
        int totalElements = pictures.size();
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();
        int lastIndex;
        if (offset + pageSize < totalElements - 1) {
            lastIndex = offset + pageSize;
        } else {
            lastIndex = totalElements;
        }
        pageable.getPageNumber();
        var content = pictures.subList(offset, lastIndex);
        return new PageImpl<>(content, pageable, totalElements);
    }
}
