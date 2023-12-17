package guru.qa.rococo.test.rest;

import guru.qa.rococo.api.painting.PaintingRestClient;
import guru.qa.rococo.jupiter.annotations.GenerateMuseum;
import guru.qa.rococo.jupiter.annotations.GeneratePictures;
import guru.qa.rococo.model.PaintingJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static guru.qa.rococo.jupiter.extensions.GenerateArtistExtension.IMAGES;
import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static guru.qa.rococo.util.FakerUtils.generateRandomName;
import static guru.qa.rococo.util.FakerUtils.generateRandomSentence;
import static guru.qa.rococo.util.Utils.getRandomImage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaintingRestTests extends BaseRestTests {

    PaintingRestClient restClient = new PaintingRestClient();

    @Test
    @DisplayName("[REST][PAINTING] Painting quantity should be equals or greater than added")
    @AllureId("22")
    @GenerateMuseum(generatePictures = @GeneratePictures(count = 2))
    void paintingQtyShouldBeEqOrGrThanAdded(List<PaintingJson> paintingJsons) throws Exception {
        List<PaintingJson> paintings = restClient.findAll();
        assertTrue(paintings.size() >= paintingJsons.size(), "painting qty less then added");
    }

    @Test
    @DisplayName("[REST][PAINTING] Paintings searching by id")
    @AllureId("23")
    @GenerateMuseum(count = 3, generatePictures = @GeneratePictures())
    void findByIdPaintingTest(List<PaintingJson> paintingJsons) throws Exception {
        for (var painting : paintingJsons) {
            PaintingJson paintingById = restClient.findById(painting.getId());
            assertEquals(painting.getTitle(), paintingById.getTitle(), "titles not equals");
            assertEquals(painting.getDescription(), paintingById.getDescription(), "descriptions are not equals");
            assertEquals(painting.getContent(), paintingById.getContent(), "contents are not equals");
            assertEquals(painting.getArtistId(), paintingById.getArtistId(), "artists are not equals");
            assertEquals(painting.getMuseumId(), paintingById.getMuseumId(), "museums are not equals");
        }
    }

    @Test
    @DisplayName("[REST][PAINTING] Paintings searching by name")
    @AllureId("24")
    @GenerateMuseum(generatePictures = @GeneratePictures())
    void findByTitlePaintingTest(List<PaintingJson> paintingJsons) throws Exception {
        for (var painting : paintingJsons) {
            PaintingJson paintingByName = restClient.findByTitle(painting.getTitle()).stream().findFirst().get();
            assertEquals(painting.getId(), paintingByName.getId(), "IDs are not equals");
            assertEquals(painting.getDescription(), paintingByName.getDescription(), "descriptions are not equals");
            assertEquals(painting.getContent(), paintingByName.getContent(), "contents are not equals");
            assertEquals(painting.getArtistId(), paintingByName.getArtistId(), "artists are not equals");
            assertEquals(painting.getMuseumId(), paintingByName.getMuseumId(), "museums are not equals");
        }
    }

    @Test
    @DisplayName("[REST][PAINTING] Painting searching by author")
    @AllureId("25")
    @GenerateMuseum(generatePictures = @GeneratePictures(count = 3))
    void findByAuthorPaintingTest(List<PaintingJson> paintingJsons) throws Exception {
        for (var painting : paintingJsons) {
            PaintingJson paintingByName = restClient.findByAuthor(painting.getArtistId()).get(0);
            assertEquals(painting.getId(), paintingByName.getId(), "IDs are not equals");
            assertEquals(painting.getDescription(), paintingByName.getDescription(), "descriptions are not equals");
            assertEquals(painting.getContent(), paintingByName.getContent(), "contents are not equals");
            assertEquals(painting.getArtistId(), paintingByName.getArtistId(), "artists are not equals");
            assertEquals(painting.getMuseumId(), paintingByName.getMuseumId(), "museums are not equals");
        }
    }

    @Test
    @DisplayName("[REST][PAINTING] Added by rest paintings should be in database")
    @AllureId("26")
    @GenerateMuseum(generatePictures = @GeneratePictures)
    void addArtistAndCheckThem(@GenerateMuseum List<PaintingJson> paintingJsons) throws Exception {
        PaintingJson museumAndArtistForPainting = paintingJsons.stream().findFirst().get();
        PaintingJson paintingJson = new PaintingJson();
        paintingJson.setTitle(generateRandomSentence(generateRandomInt(2, 4)));
        paintingJson.setDescription(generateRandomSentence(generateRandomInt(2, 4)));
        paintingJson.setContent(getRandomImage(IMAGES));
        paintingJson.setArtistId(museumAndArtistForPainting.getArtistId());
        paintingJson.setMuseumId(museumAndArtistForPainting.getMuseumId());
        PaintingJson painting = restClient.createPainting(paintingJson);
        PaintingJson paintingById = restClient.findById(painting.getId());
        assertEquals(painting.getTitle(), paintingById.getTitle(), "titles not equals");
        assertEquals(painting.getDescription(), paintingById.getDescription(), "descriptions are not equals");
        assertEquals(painting.getContent(), paintingById.getContent(), "contents are not equals");
        assertEquals(painting.getArtistId(), paintingById.getArtistId(), "artists are not equals");
        assertEquals(painting.getMuseumId(), paintingById.getMuseumId(), "museums are not equals");
    }

    @Test
    @DisplayName("[REST][PAINTING] Editing painting by rest and check changes")
    @AllureId("27")
    @GenerateMuseum(generatePictures = @GeneratePictures)
    void editArtistTest(List<PaintingJson> paintingJsons) throws Exception {
        PaintingJson painting = paintingJsons.get(0);
        PaintingJson paintingToChanging = restClient.findById(painting.getId());
        String newTitle = generateRandomName();
        String newDescription = generateRandomSentence(generateRandomInt(2, 3));
        paintingToChanging.setTitle(newTitle);
        paintingToChanging.setDescription(newDescription);
        paintingToChanging.setContent(getRandomImage(IMAGES));
        PaintingJson paintingAfterEdit = restClient.editPainting(paintingToChanging);

        assertEquals(paintingAfterEdit.getId(), paintingToChanging.getId(), "IDs not equals");
        assertEquals(paintingAfterEdit.getTitle(), paintingToChanging.getTitle(), "titles not equals");
        assertEquals(paintingAfterEdit.getDescription(), paintingToChanging.getDescription(), "descriptions are not equals");
        assertEquals(paintingAfterEdit.getContent(), paintingToChanging.getContent(), "contents are not equals");
        assertEquals(paintingAfterEdit.getArtistId(), paintingToChanging.getArtistId(), "artists are not equals");
        assertEquals(paintingAfterEdit.getMuseumId(), paintingToChanging.getMuseumId(), "museums are not equals");

    }


}
