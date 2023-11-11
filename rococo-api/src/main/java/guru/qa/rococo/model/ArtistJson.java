package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoApiServiceConfig;
import jakarta.validation.constraints.Size;

import java.util.UUID;

import static guru.qa.rococo.config.RococoApiServiceConfig.TEN_MB;


public record ArtistJson(
        @JsonProperty("id") UUID id,
        @JsonProperty("name") String name,
        @JsonProperty("biography")
        String biography,
        @JsonProperty("photo")
        @Size(max = TEN_MB)
        String photo) {

}
