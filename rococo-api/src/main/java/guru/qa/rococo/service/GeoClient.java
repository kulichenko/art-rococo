package guru.qa.rococo.service;

import guru.qa.rococo.controller.UserController;
import guru.qa.rococo.model.CountryJson;
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
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
@Qualifier("rest")
public class GeoClient {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final WebClient webClient;
    private final String geoBaseUri;

    @Autowired
    public GeoClient(WebClient webClient,
                     @Value("${rococo-geo.base-uri}") String geoBaseUri) {
        this.webClient = webClient;
        this.geoBaseUri = geoBaseUri;
    }

    public Page<CountryJson> allCountries(PageRequest pageRequest) {
        URI uri = UriComponentsBuilder.fromHttpUrl(geoBaseUri + "/geo").build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(CountryJson.class)
                .collectList()
                .map(a -> createPage(a, pageRequest))
                .block();
    }

    public List<CountryJson> findById(List<String> ids) {
        URI uri = UriComponentsBuilder.fromHttpUrl(geoBaseUri + "/geo/findByIds")
                .build().toUri();

        return webClient.post()
                .uri(uri)
                .body(Mono.just(ids), List.class)
                .retrieve()
                .bodyToFlux(CountryJson.class)
                .collectList()
                .block();
    }

    private Page<CountryJson> createPage(List<CountryJson> geo, Pageable pageable) {
        int totalElements = geo.size();
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();
        int lastIndex;
        if (offset + pageSize < totalElements - 1) {
            lastIndex = offset + pageSize;
        } else {
            lastIndex = totalElements;
        }
        pageable.getPageNumber();
        var content = geo.subList(offset, lastIndex);
        return new PageImpl<>(content, pageable, totalElements);
    }
}
