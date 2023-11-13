package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import guru.qa.rococo.data.PaintingEntity;
import jakarta.annotation.Nonnull;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaintingJson(
        UUID id,
        String title,
        String description,
        String content,
        UUID museumId,
        UUID artistId
) {
    public static @Nonnull PaintingJson fromEntity(@Nonnull PaintingEntity entity) {

        return new PaintingJson(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getContent() != null && entity.getContent().length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null,
                entity.getMuseumId(),
                entity.getArtistId()
        );
    }
}
