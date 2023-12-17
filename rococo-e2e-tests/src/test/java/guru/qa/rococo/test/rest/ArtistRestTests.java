package guru.qa.rococo.test.rest;

import guru.qa.rococo.api.artist.ArtistRestClient;
import guru.qa.rococo.jupiter.annotations.GenerateArtist;
import guru.qa.rococo.model.ArtistJson;
import io.qameta.allure.AllureId;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static guru.qa.rococo.jupiter.extensions.GenerateArtistExtension.IMAGES;
import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static guru.qa.rococo.util.FakerUtils.generateRandomName;
import static guru.qa.rococo.util.FakerUtils.generateRandomSentence;
import static guru.qa.rococo.util.Utils.getRandomImage;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tags({@Tag("REST"), @Tag("ARTIST")})
@Epic("ARTIST")
public class ArtistRestTests extends BaseRestTests {
    private final ArtistRestClient restClient = new ArtistRestClient();

    @Test
    @DisplayName("[REST][ARTIST] Artists quantity should be equals or greater than added")
    @AllureId("17")
    @GenerateArtist(count = 5)
    void artistsQtyShouldBeEqOrGrThanAdded(List<ArtistJson> artistJsons) throws Exception {
        List<ArtistJson> artists = restClient.findAll();
        step("Check that artists quantity equals or greater than generated", () ->
                assertTrue(artists.size() >= artistJsons.size(), "artist's qty less then added"));
    }

    @Test
    @DisplayName("[REST][ARTIST] Artists from the database are available when searching by id")
    @AllureId("18")
    @GenerateArtist(count = 5)
    void findByIdAddedArtistTest(List<ArtistJson> artistJsons) throws Exception {
        for (var artist : artistJsons) {
            ArtistJson artistById = restClient.findById(artist.getId());
            compareArtistJsons(artist, artistById);
        }
    }

    @Test
    @DisplayName("[REST][ARTIST] Artists from the database are available when searching by name")
    @AllureId("19")
    @GenerateArtist(count = 5)
    void findByNameAddedArtistTest(List<ArtistJson> artistJsons) throws Exception {
        for (var artist : artistJsons) {
            ArtistJson artistByName = restClient.findByName(artist.getName()).stream().findFirst().get();
            step("Check artist's id", () ->
                    assertEquals(artist.getId(), artistByName.getId(), "IDs not equals"));
            step("Check artist's biography", () ->
                    assertEquals(artist.getBiography(), artistByName.getBiography(), "biographies not equals"));
            step("Check artist's photo", () ->
                    assertEquals(artist.getPhoto(), artistByName.getPhoto(), "photos not equals"));
        }
    }

    @Test
    @DisplayName("[REST][ARTIST] Added by rest artists should be in database")
    @AllureId("20")
    void addArtistAndCheckThem() throws Exception {
        ArtistJson artistJson = new ArtistJson();
        artistJson.setName(generateRandomName());
        artistJson.setBiography(generateRandomSentence(generateRandomInt(2, 4)));
        artistJson.setPhoto(getRandomImage(IMAGES));
        ArtistJson createdArtist = restClient.createArtist(artistJson);
        compareArtistJsons(createdArtist, artistJson);
    }

    @Test
    @DisplayName("[REST][ARTIST] Editing artist by rest and check changes")
    @AllureId("21")
    @GenerateArtist()
    void editArtistTest(List<ArtistJson> artistJsons) throws Exception {
        ArtistJson artist = artistJsons.get(0);
        ArtistJson artistToChanging = restClient.findById(artist.getId());
        String newName = generateRandomName();
        String newBiography = generateRandomSentence(generateRandomInt(2, 3));
        artistToChanging.setName(newName);
        artistToChanging.setBiography(newBiography);
        ArtistJson artistJsonAfterEditing = restClient.editArtist(artistToChanging);
        step("Check artist's id", () ->
                assertEquals(artistJsonAfterEditing.getId(), artistToChanging.getId(), "IDs not equals"));
        compareArtistJsons(artistJsonAfterEditing, artistToChanging);

    }

    private void compareArtistJsons(ArtistJson leftArtistJson, ArtistJson rightArtistJson) {
        step("Check artist's name", () ->
                assertEquals(leftArtistJson.getName(), rightArtistJson.getName(), "names not equals"));
        step("Check artist's biography", () ->
                assertEquals(leftArtistJson.getBiography(), rightArtistJson.getBiography(), "biographies not equals"));
        step("Check artist's photo", () ->
                assertEquals(leftArtistJson.getPhoto(), rightArtistJson.getPhoto(), "photos not equals"));
    }


}
