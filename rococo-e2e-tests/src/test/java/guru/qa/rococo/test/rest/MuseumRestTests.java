package guru.qa.rococo.test.rest;

import guru.qa.rococo.api.museum.MuseumRestClient;
import guru.qa.rococo.jupiter.annotations.GenerateMuseum;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.util.Utils;
import io.qameta.allure.AllureId;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static guru.qa.rococo.jupiter.extensions.GenerateArtistExtension.IMAGES;
import static guru.qa.rococo.util.FakerUtils.generateNewMuseumTitle;
import static guru.qa.rococo.util.FakerUtils.generateRandomCity;
import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static guru.qa.rococo.util.FakerUtils.generateRandomSentence;
import static guru.qa.rococo.util.Utils.getRandomImage;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tags({@Tag("REST"), @Tag("MUSEUM")})
@Epic("MUSEUM")
public class MuseumRestTests extends BaseRestTests {

    private final MuseumRestClient restClient = new MuseumRestClient();
    private final Utils utils = new Utils();


    @Test
    @DisplayName("[REST][MUSEUM] Museum quantity should be equals or greater than generated")
    @AllureId("28")
    @GenerateMuseum(count = 2)
    void museumQtyShouldBeEqOrGrThanAdded(List<PaintingJson> paintingJsons) throws Exception {
        List<MuseumJson> generatedMuseums = paintingJsons.stream().map(PaintingJson::getMuseum).toList();
        List<MuseumJson> museumsFromDb = restClient.findAll();
        step("Check that Museum quantity equals or greater than generated", () ->
                assertTrue(museumsFromDb.size() >= generatedMuseums.size(), "museum qty less then added"));
    }

    @Test
    @DisplayName("[REST][MUSEUM] Museums searching by id")
    @AllureId("29")
    @GenerateMuseum(count = 3)
    void findByIdMuseumTest(List<PaintingJson> paintingJsons) throws Exception {
        List<MuseumJson> generatedMuseums = paintingJsons.stream().map(PaintingJson::getMuseum).toList();
        for (var museum : generatedMuseums) {
            MuseumJson museumById = restClient.findById(museum.getId());
            compareMuseumJsons(museum, museumById);
        }
    }

    @Test
    @DisplayName("[REST][MUSEUM] Museums searching by name")
    @AllureId("30")
    @GenerateMuseum()
    void findByTitleMuseumTest(List<PaintingJson> paintingJsons) throws Exception {
        List<MuseumJson> generatedMuseums = paintingJsons.stream().map(PaintingJson::getMuseum).toList();
        for (var museum : generatedMuseums) {
            MuseumJson museumByName = restClient.findByTitle(museum.getTitle()).stream().findFirst().get();
            compareMuseumJsons(museum, museumByName);
        }
    }

    @Test
    @DisplayName("[REST][MUSEUM] Added by rest museums should be in database")
    @AllureId("31")
    void addMuseumAndCheckThem() throws Exception {
        MuseumJson museumJson = new MuseumJson();
        String city = generateRandomCity();
        museumJson.setTitle(generateRandomSentence(generateRandomInt(2, 4)));
        museumJson.setDescription(generateRandomSentence(generateRandomInt(2, 4)));
        museumJson.setPhoto(getRandomImage(IMAGES));
        museumJson.setCity(city);
        GeoJson geoJson = utils.getRandomGeo(city, 0, 180);
        museumJson.setCountryId(geoJson.getCountry().getId());
        MuseumJson museum = restClient.createMuseum(museumJson);
        final MuseumJson museumById = restClient.findById(museum.getId());

        compareMuseumJsons(museum, museumById);
    }

    @Test
    @DisplayName("[REST][MUSEUM] Editing museum by rest and check changes")
    @AllureId("32")
    @GenerateMuseum()
    void editMuseumTest(List<PaintingJson> paintingJsons) throws Exception {
        MuseumJson generatedMuseum = paintingJsons.get(0).getMuseum();
        MuseumJson museumForChanging = restClient.findById(generatedMuseum.getId());
        compareMuseumJsons(generatedMuseum, museumForChanging);
        String newTitle = generateNewMuseumTitle();
        String newDescription = generateRandomSentence(generateRandomInt(2, 3));
        museumForChanging.setTitle(newTitle);
        museumForChanging.setDescription(newDescription);
        museumForChanging.setPhoto(getRandomImage(IMAGES));
        String newCity = generateRandomCity();
        museumForChanging.setCity(newCity);
        GeoJson newGeoJson = utils.getRandomGeo(newCity, 0, 180);
        museumForChanging.setCountryId(newGeoJson.getCountry().getId());
        final MuseumJson museumAfterEdit = restClient.editMuseum(museumForChanging);

        compareMuseumJsons(museumAfterEdit, museumForChanging);

    }

    private void compareMuseumJsons(MuseumJson museum, MuseumJson museumForChanging) {
        step("Check museum id", () ->
                assertEquals(museum.getId(), museumForChanging.getId(), "IDs are not equals"));
        step("Check museum title", () ->
                assertEquals(museum.getTitle(), museumForChanging.getTitle(), "titles not equals"));
        step("Check museum description", () ->
                assertEquals(museum.getDescription(), museumForChanging.getDescription(), "descriptions are not equals"));
        step("Check museum photo", () ->
                assertEquals(museum.getPhoto(), museumForChanging.getPhoto(), "photos are not equals"));
        step("Check museum city", () ->
                assertEquals(museum.getCity(), museumForChanging.getCity(), "cities are not equals"));
        step("Check museum's country id", () ->
                assertEquals(museum.getCountryId(), museumForChanging.getCountryId(),
                        "country ids are not equals"));
    }
}
