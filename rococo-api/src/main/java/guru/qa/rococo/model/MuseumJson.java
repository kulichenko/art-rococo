package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MuseumJson {
    private UUID id;
    private String title;
    private String description;
    private String photo;
    private String city;
    private UUID countryId;
    private GeoJson geo;

}
