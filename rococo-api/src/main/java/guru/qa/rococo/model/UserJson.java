package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoApiServiceConfig;
import jakarta.validation.constraints.Size;

import javax.annotation.Nonnull;
import java.util.UUID;


public record UserJson(
        @JsonProperty("id") UUID id,
        @JsonProperty("username") String username,
        @JsonProperty("firstname")
        @Size(max = 30, message = "First name can`t be longer than 30 characters")
        String firstname,
        @JsonProperty("surname")
        @Size(max = 50, message = "Surname can`t be longer than 50 characters")
        String surname,
        @JsonProperty("photo")
        @Size(max = RococoApiServiceConfig.TEN_MB)
        String photo)
{
        public @Nonnull UserJson addUsername(@Nonnull String username) {
                return new UserJson(id, firstname, surname, photo, username);
        }
}
