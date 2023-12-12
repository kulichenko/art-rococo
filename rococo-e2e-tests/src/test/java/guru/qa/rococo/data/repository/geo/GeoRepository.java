package guru.qa.rococo.data.repository.geo;

import guru.qa.rococo.data.entity.geo.GeoEntity;


public interface GeoRepository {

    GeoEntity getRandomCountry(int start, int end);
}
