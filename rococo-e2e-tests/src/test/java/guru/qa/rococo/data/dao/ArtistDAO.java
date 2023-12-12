package guru.qa.rococo.data.dao;

import guru.qa.rococo.data.entity.artist.ArtistEntity;

public interface ArtistDAO {

    int addArtist(ArtistEntity artistEntity);

    ArtistEntity findByName(ArtistEntity artistEntity);

    ArtistEntity findById(ArtistEntity artistEntity);
}
