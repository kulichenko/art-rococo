package guru.qa.rococo.data.repository.geo;

import guru.qa.rococo.data.dao.impl.GeoDAOHibernate;

public class GeoRepositoryHibernate extends AbstractGeoRepository {
    public GeoRepositoryHibernate() {
        super(new GeoDAOHibernate());
    }
}
