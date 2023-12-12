package guru.qa.rococo.data.repository.geo;

import guru.qa.rococo.data.dao.GeoDAO;
import guru.qa.rococo.data.entity.geo.GeoEntity;

public abstract class AbstractGeoRepository implements GeoRepository {
    private final GeoDAO geoDAO;

    public AbstractGeoRepository(GeoDAO geoDAO) {
        this.geoDAO = geoDAO;
    }

    @Override
    public GeoEntity getRandomCountry(int start, int end) {
        return geoDAO.getRandomCountry(start, end);
    }
}
