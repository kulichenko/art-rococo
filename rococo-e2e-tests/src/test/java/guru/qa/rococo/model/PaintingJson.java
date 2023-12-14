package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Getter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaintingJson {
    UUID id;
    String title;
    String description;
    String content;
    UUID museumId;
    UUID artistId;
    ArtistJson artist;
    MuseumJson museum;
}
