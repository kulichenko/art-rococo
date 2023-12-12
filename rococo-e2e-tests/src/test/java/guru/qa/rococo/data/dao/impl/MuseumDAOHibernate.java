package guru.qa.rococo.data.dao.impl;

import guru.qa.rococo.data.DataBase;
import guru.qa.rococo.data.dao.MuseumDAO;
import guru.qa.rococo.data.entity.museum.MuseumEntity;
import guru.qa.rococo.data.jpa.EntityManagerFactoryProvider;
import guru.qa.rococo.data.jpa.JpaService;

import java.util.UUID;

public class MuseumDAOHibernate extends JpaService implements MuseumDAO {
    public MuseumDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(DataBase.MUSEUM).createEntityManager());
    }

    @Override
    public int addMuseum(MuseumEntity museumEntity) {
        persist(museumEntity);
        return 0;
    }

    @Override
    public MuseumEntity findByName(MuseumEntity museumEntity) {
        String title = museumEntity.getTitle();
        return em.createQuery("SELECT m FROM MuseumEntity m WHERE m.title=:title", MuseumEntity.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    @Override
    public MuseumEntity findById(MuseumEntity museumEntity) {
        UUID id = museumEntity.getId();
        return em.createQuery("SELECT m FROM MuseumEntity m WHERE m.id=:id", MuseumEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
