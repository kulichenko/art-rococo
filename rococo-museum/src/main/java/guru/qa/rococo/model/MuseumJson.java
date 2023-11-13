package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import guru.qa.rococo.data.MuseumEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MuseumJson(
        UUID id,
        String title,
        String description,
        String photo,
        String city,
        UUID countryId

) {
    public static MuseumJson fromEntity(MuseumEntity entity) {

        return new MuseumJson(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPhoto() != null && entity.getPhoto().length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null,
                entity.getCity(),
                entity.getCountryId()
        );
    }

}
