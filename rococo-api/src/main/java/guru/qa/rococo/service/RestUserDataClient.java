package guru.qa.rococo.service;

import guru.qa.rococo.model.UserJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Qualifier("rest")
public class RestUserDataClient implements UserDataClient {
    private final WebClient webClient;
    private final String userdataBaseUri;

    @Autowired
    public RestUserDataClient(WebClient webClient,
                              @Value("${rococo-userdata.base-uri}") String userdataBaseUri) {
        this.webClient = webClient;
        this.userdataBaseUri = userdataBaseUri;
    }


    @Nonnull
    @Override
    public UserJson currentUser(@Nonnull String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(userdataBaseUri + "/user").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    @Override
    public @Nonnull UserJson editUser(@Nonnull UserJson user) {
        return webClient.patch()
                .uri(userdataBaseUri + "/user")
                .body(Mono.just(user), UserJson.class)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }
}
