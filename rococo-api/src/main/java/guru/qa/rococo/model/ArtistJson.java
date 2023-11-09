package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoApiServiceConfig;
import jakarta.validation.constraints.Size;

import java.util.UUID;


public record ArtistJson(
        @JsonProperty("id") UUID id,
        @JsonProperty("name") String name,
        @JsonProperty("biography")
        String biography,
        @JsonProperty("photo")
        @Size(max = RococoApiServiceConfig.TEN_MB)
        byte[] photo) {

}
