package guru.qa.rococo.data.repository.artist;

import guru.qa.rococo.data.dao.ArtistDAO;
import guru.qa.rococo.data.entity.artist.ArtistEntity;

public abstract class AbstractArtistRepository implements ArtistRepository {
    private final ArtistDAO artistDAO;

    protected AbstractArtistRepository(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    public int addArtist(ArtistEntity artistEntity) {
        artistDAO.addArtist(artistEntity);
        return 0;
    }

    public ArtistEntity findByName(ArtistEntity artistEntity) {
        return artistDAO.findById(artistEntity);
    }

    public ArtistEntity findById(ArtistEntity artistEntity) {
        return artistDAO.findById(artistEntity);
    }
}
