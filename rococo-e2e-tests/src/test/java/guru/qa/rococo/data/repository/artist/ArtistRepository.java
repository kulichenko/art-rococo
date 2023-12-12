package guru.qa.rococo.data.repository.artist;

import guru.qa.rococo.data.entity.artist.ArtistEntity;

public interface ArtistRepository {
    int addArtist(ArtistEntity artistEntity);

    ArtistEntity findByName(ArtistEntity artistEntity);

    ArtistEntity findById(ArtistEntity artistEntity);
}
