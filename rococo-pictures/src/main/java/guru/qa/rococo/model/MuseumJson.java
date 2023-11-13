package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MuseumJson(
        UUID id,
        String title,
        String description,
        String photo,
        String city,
        UUID countryId,
        GeoJson geo

) {
}
