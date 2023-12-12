package guru.qa.rococo.data.dao.impl;

import guru.qa.rococo.data.DataBase;
import guru.qa.rococo.data.dao.ArtistDAO;
import guru.qa.rococo.data.entity.artist.ArtistEntity;
import guru.qa.rococo.data.jpa.EntityManagerFactoryProvider;
import guru.qa.rococo.data.jpa.JpaService;

import java.util.UUID;

public class ArtistDAOHibernate extends JpaService implements ArtistDAO {
    public ArtistDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(DataBase.ARTIST).createEntityManager());
    }

    @Override
    public int addArtist(ArtistEntity artist) {
        persist(artist);
        return 0;
    }

    @Override
    public ArtistEntity findByName(ArtistEntity artistEntity) {
        String name = artistEntity.getName();
        return em.createQuery("SELECT a FROM ArtistEntity a WHERE a.name=:name", ArtistEntity.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public ArtistEntity findById(ArtistEntity artistEntity) {
        UUID id = artistEntity.getId();
        return em.createQuery("SELECT a FROM ArtistEntity a WHERE a.id=:id", ArtistEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
