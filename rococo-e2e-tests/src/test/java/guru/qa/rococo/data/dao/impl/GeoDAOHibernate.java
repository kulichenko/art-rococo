package guru.qa.rococo.data.dao.impl;

import guru.qa.rococo.data.DataBase;
import guru.qa.rococo.data.dao.GeoDAO;
import guru.qa.rococo.data.entity.geo.GeoEntity;
import guru.qa.rococo.data.jpa.EntityManagerFactoryProvider;
import guru.qa.rococo.data.jpa.JpaService;

import static guru.qa.rococo.util.FakerUtils.generateRandomInt;

public class GeoDAOHibernate extends JpaService implements GeoDAO {
    public GeoDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(DataBase.GEO).createEntityManager());
    }

    @Override
    public GeoEntity getRandomCountry(int start, int end) {
        int offsetValue = generateRandomInt(start, end);
        return em.createQuery("SELECT g from GeoEntity g", GeoEntity.class)
                .setFirstResult(offsetValue)
                .setMaxResults(1)
                .getSingleResult();
    }
}
