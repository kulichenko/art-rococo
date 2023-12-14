package guru.qa.rococo.test.rest;

import guru.qa.rococo.api.artist.ArtistRestClient;
import guru.qa.rococo.jupiter.annotations.GenerateArtist;
import guru.qa.rococo.model.ArtistJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtistRestTests extends BaseRestTests {
    private final ArtistRestClient restClient = new ArtistRestClient();

    @Test
    @DisplayName("[REST][ARTIST] ")
    @AllureId("17")
    @GenerateArtist(count = 5)
    void artistTest(List<ArtistJson> artistJsons) throws Exception {
        List<ArtistJson> artists = restClient.findAll();
        assertTrue(artists.size() >= artistJsons.size());
    }

}
