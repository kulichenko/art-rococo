package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaintingJson(
        UUID id,
        String title,
        String description,
        String content,
        UUID museumId,
        UUID artistId,
        ArtistJson artist,
        MuseumJson museum
) {
}
