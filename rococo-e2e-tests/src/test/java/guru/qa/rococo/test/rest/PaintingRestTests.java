package guru.qa.rococo.test.rest;

import guru.qa.rococo.api.painting.PaintingRestClient;
import guru.qa.rococo.jupiter.annotations.GenerateMuseum;
import guru.qa.rococo.jupiter.annotations.GeneratePictures;
import guru.qa.rococo.model.PaintingJson;
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

@Tags({@Tag("REST"), @Tag("PAINTING")})
@Epic("PAINTING")
public class PaintingRestTests extends BaseRestTests {

    PaintingRestClient restClient = new PaintingRestClient();

    @Test
    @DisplayName("[REST][PAINTING] Painting quantity should be equals or greater than added")
    @AllureId("22")
    @GenerateMuseum(generatePictures = @GeneratePictures(count = 2))
    void paintingQtyShouldBeEqOrGrThanAdded(List<PaintingJson> paintingJsons) throws Exception {
        List<PaintingJson> paintings = restClient.findAll();
        step("Check that painting quantity equals or greater than generated", () ->
                assertTrue(paintings.size() >= paintingJsons.size(), "painting qty less then added"));
    }

    @Test
    @DisplayName("[REST][PAINTING] Paintings searching by id")
    @AllureId("23")
    @GenerateMuseum(count = 3, generatePictures = @GeneratePictures())
    void findByIdPaintingTest(List<PaintingJson> paintingJsons) throws Exception {
        for (var painting : paintingJsons) {
            PaintingJson paintingById = restClient.findById(painting.getId());
            comparePaintingJsons(painting, paintingById);
        }
    }

    @Test
    @DisplayName("[REST][PAINTING] Paintings searching by name")
    @AllureId("24")
    @GenerateMuseum(generatePictures = @GeneratePictures())
    void findByTitlePaintingTest(List<PaintingJson> paintingJsons) throws Exception {
        for (var painting : paintingJsons) {
            PaintingJson paintingByName = restClient.findByTitle(painting.getTitle()).stream().findFirst().get();
            step("Check painting id", () ->
                    assertEquals(painting.getId(), paintingByName.getId(), "IDs are not equals"));
            step("Check painting description", () ->
                    assertEquals(painting.getDescription(), paintingByName.getDescription(), "descriptions are not equals"));
            step("Check painting content", () ->
                    assertEquals(painting.getContent(), paintingByName.getContent(), "contents are not equals"));
            step("Check painting artist id", () ->
                    assertEquals(painting.getArtistId(), paintingByName.getArtistId(), "artists are not equals"));
            step("Check painting museum id", () ->
                    assertEquals(painting.getMuseumId(), paintingByName.getMuseumId(), "museums are not equals"));
        }
    }

    @Test
    @DisplayName("[REST][PAINTING] Painting searching by author")
    @AllureId("25")
    @GenerateMuseum(generatePictures = @GeneratePictures(count = 3))
    void findByAuthorPaintingTest(List<PaintingJson> paintingJsons) throws Exception {
        for (var painting : paintingJsons) {
            PaintingJson paintingByAuthor = restClient.findByAuthor(painting.getArtistId()).get(0);
            step("Check painting id", () ->
                    assertEquals(painting.getId(), paintingByAuthor.getId(), "IDs are not equals"));
            comparePaintingJsons(painting, paintingByAuthor);
        }
    }

    @Test
    @DisplayName("[REST][PAINTING] Added by rest paintings should be in database")
    @AllureId("26")
    @GenerateMuseum(generatePictures = @GeneratePictures)
    void addPaintingAndCheckThem(@GenerateMuseum List<PaintingJson> paintingJsons) throws Exception {
        PaintingJson museumAndArtistForPainting = paintingJsons.stream().findFirst().get();
        PaintingJson paintingJson = new PaintingJson();
        paintingJson.setTitle(generateRandomSentence(generateRandomInt(2, 4)));
        paintingJson.setDescription(generateRandomSentence(generateRandomInt(2, 4)));
        paintingJson.setContent(getRandomImage(IMAGES));
        paintingJson.setArtistId(museumAndArtistForPainting.getArtistId());
        paintingJson.setMuseumId(museumAndArtistForPainting.getMuseumId());
        PaintingJson painting = restClient.createPainting(paintingJson);
        PaintingJson paintingById = restClient.findById(painting.getId());
        comparePaintingJsons(paintingJson, paintingById);
    }

    @Test
    @DisplayName("[REST][PAINTING] Editing painting by rest and check changes")
    @AllureId("27")
    @GenerateMuseum(generatePictures = @GeneratePictures)
    void editPaintingTest(List<PaintingJson> paintingJsons) throws Exception {
        PaintingJson painting = paintingJsons.get(0);
        PaintingJson paintingToChanging = restClient.findById(painting.getId());
        String newTitle = generateRandomName();
        String newDescription = generateRandomSentence(generateRandomInt(2, 3));
        paintingToChanging.setTitle(newTitle);
        paintingToChanging.setDescription(newDescription);
        paintingToChanging.setContent(getRandomImage(IMAGES));
        PaintingJson paintingAfterEdit = restClient.editPainting(paintingToChanging);
        step("Check painting id", () ->
                assertEquals(paintingAfterEdit.getId(), paintingToChanging.getId(), "IDs not equals"));
        comparePaintingJsons(paintingAfterEdit, paintingToChanging);

    }

    private void comparePaintingJsons(PaintingJson leftPaintingJson, PaintingJson rightPaintingJson) {
        step("Check painting title", () ->
                assertEquals(leftPaintingJson.getTitle(), rightPaintingJson.getTitle(), "titles not equals"));
        step("Check painting description", () ->
                assertEquals(leftPaintingJson.getDescription(), rightPaintingJson.getDescription(), "descriptions are not equals"));
        step("Check painting content", () ->
                assertEquals(leftPaintingJson.getContent(), rightPaintingJson.getContent(), "contents are not equals"));
        step("Check painting artist id", () ->
                assertEquals(leftPaintingJson.getArtistId(), rightPaintingJson.getArtistId(), "artists are not equals"));
        step("Check painting museum id", () ->
                assertEquals(leftPaintingJson.getMuseumId(), rightPaintingJson.getMuseumId(), "museums are not equals"));
    }


}
