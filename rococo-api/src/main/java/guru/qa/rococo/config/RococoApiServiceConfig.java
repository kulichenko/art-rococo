package guru.qa.rococo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RococoApiServiceConfig {

    public static final int TEN_MB = 10 * 1024 * 1024;

    private final String rococoUserdataBaseUri;

    @Autowired
    public RococoApiServiceConfig(@Value("${rococo-userdata.base-uri}") String rococoUserdataBaseUri) {
        this.rococoUserdataBaseUri = rococoUserdataBaseUri;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(TEN_MB))
                        .build())
                .build();
    }
}
