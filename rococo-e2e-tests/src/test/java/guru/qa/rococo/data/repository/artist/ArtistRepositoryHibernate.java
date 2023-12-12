package guru.qa.rococo.data.repository.artist;

import guru.qa.rococo.data.dao.impl.ArtistDAOHibernate;

public class ArtistRepositoryHibernate extends AbstractArtistRepository {
    public ArtistRepositoryHibernate() {
        super(new ArtistDAOHibernate());
    }

}
