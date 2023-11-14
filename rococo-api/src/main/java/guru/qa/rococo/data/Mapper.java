package guru.qa.rococo.data;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.ArtistClient;
import guru.qa.rococo.service.GeoClient;
import guru.qa.rococo.service.MuseumClient;
import guru.qa.rococo.service.PaintingClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Mapper {
    private final ArtistClient artistClient;
    private final PaintingClient paintingClient;
    private final MuseumClient museumClient;
    private final GeoClient geoClient;

    public Mapper(ArtistClient artistClient, PaintingClient paintingClient, MuseumClient museumClient, GeoClient geoClient) {
        this.artistClient = artistClient;
        this.paintingClient = paintingClient;
        this.museumClient = museumClient;
        this.geoClient = geoClient;
    }


    /**
     * Add Geo object to Page with Museum jsons
     *
     * @param museumJsonPage
     * @return Page<MuseumJson>
     */
    public Page<MuseumJson> putGeoObjectsToMuseums(Page<MuseumJson> museumJsonPage) {

        var countryIds = museumJsonPage
                .getContent()
                .stream()
                .map(MuseumJson::getCountryId)
                .map(String::valueOf)
                .collect(Collectors.toList());
        var countryJsons = geoClient.findById(countryIds);
        for (var countryJson : countryJsons) {
            for (var museumJson : museumJsonPage) {
                setCountryAndCityToGeo(countryJson, museumJson);
            }
        }
        return museumJsonPage;
    }

    /**
     * Add Geo object to single MuseumJson
     *
     * @param museumJson
     * @return MuseumJson
     */
    public MuseumJson putGeoObjectToMuseum(MuseumJson museumJson) {
        var countryId = museumJson.getCountryId();
        var countryJsons = geoClient.findById(Collections.singletonList(String.valueOf(countryId)));
        for (var countryJson : countryJsons) {
            setCountryAndCityToGeo(countryJson, museumJson);
        }
        return museumJson;
    }

    /**
     * Add city and country id from Geo object to single MuseumJson
     *
     * @param museumJson
     * @return MuseumJson
     */
    public MuseumJson addCountryIdAndCityToMuseumFromGeo(MuseumJson museumJson) {
        Optional.ofNullable(museumJson.getGeo().getCity()).ifPresent(museumJson::setCity);
        Optional.ofNullable(museumJson.getGeo().getCountry().getId()).ifPresent(museumJson::setCountryId);
        return museumJson;
    }

    public Page<PaintingJson> putMuseumsAndArtistsToPaintings(Page<PaintingJson> paintingJsonPage) {
        var museumJsons = paintingJsonPage
                .getContent()
                .stream()
                .filter(Objects::nonNull)
                .map(PaintingJson::getMuseumId)
                .map(id -> museumClient.findById(String.valueOf(id)))
                .map(this::putGeoObjectToMuseum)
                .collect(Collectors.toList());

        for (var museumJson : museumJsons) {
            for (var paintingJson : paintingJsonPage) {
                if (paintingJson.getMuseumId().equals(museumJson.getId())) {
                    paintingJson.setMuseum(museumJson);
                }
            }
        }

        var artistJsons = paintingJsonPage
                .getContent()
                .stream()
                .filter(Objects::nonNull)
                .map(PaintingJson::getArtistId)
                .map(id -> artistClient.findById(String.valueOf(id)))
                .collect(Collectors.toList());


        for (var artistJson : artistJsons) {
            for (var paintingJson : paintingJsonPage) {
                if (paintingJson.getArtistId().equals(artistJson.getId())) {
                    paintingJson.setArtist(artistJson);
                }
            }
        }

        return paintingJsonPage;
    }

    public PaintingJson setMuseumIdAndArtistIdToPainting(PaintingJson paintingJson) {
        Optional.ofNullable(paintingJson.getMuseum().getId()).ifPresent(paintingJson::setMuseumId);
        Optional.ofNullable(paintingJson.getArtist().getId()).ifPresent(paintingJson::setArtistId);
        return paintingJson;
    }

    public PaintingJson putMuseumAndArtistObjectsToPainting(PaintingJson paintingJson) {
        var museum = putGeoObjectToMuseum(museumClient.findById(String.valueOf(paintingJson.getMuseumId())));
        var artist = artistClient.findById(String.valueOf(paintingJson.getArtistId()));
        paintingJson.setArtist(artist);
        paintingJson.setMuseum(museum);
        return paintingJson;
    }

    private static void setCountryAndCityToGeo(CountryJson countryJson, MuseumJson museumJson) {
        if (museumJson.getCountryId().equals(countryJson.getId())) {
            var geo = new GeoJson();
            geo.setCountry(countryJson);
            geo.setCity(museumJson.getCity());
            museumJson.setGeo(geo);
        }
    }

}
