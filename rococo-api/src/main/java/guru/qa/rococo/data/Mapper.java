package guru.qa.rococo.data;

import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.ArtistClient;
import guru.qa.rococo.service.GeoClient;
import guru.qa.rococo.service.MuseumClient;
import guru.qa.rococo.service.PaintingClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class Mapper {
    private ArtistClient artistClient;

    private PaintingClient paintingClient;
    private MuseumClient museumClient;

    private final GeoClient geoClient;

    public Mapper(ArtistClient artistClient, PaintingClient paintingClient, MuseumClient museumClient, GeoClient geoClient) {
        this.artistClient = artistClient;
        this.paintingClient = paintingClient;
        this.museumClient = museumClient;
        this.geoClient = geoClient;
    }


    public Page<MuseumJson> mapGeoToMuseum(Page<MuseumJson> museumJsonPage) {

        var countryIds = museumJsonPage
                .getContent()
                .stream()
                .map(MuseumJson::getCountryId)
                .map(String::valueOf)
                .collect(Collectors.toList());

        var countryJsons = geoClient.findById(countryIds);

        for (var countryJson : countryJsons) {
            for (var museumJson : museumJsonPage) {
                if (museumJson.getCountryId().equals(countryJson.getId())) {
                    var geo = new GeoJson();
                    geo.setCountry(countryJson);
                    geo.setCity(museumJson.getCity());
                    museumJson.setGeo(geo);
                    museumJson.setCountryId(null);
                }
            }
        }
        return museumJsonPage;
    }

}
