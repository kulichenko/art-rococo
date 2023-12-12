package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotations.ApiLogin;
import guru.qa.rococo.jupiter.annotations.GenerateMuseum;
import guru.qa.rococo.jupiter.annotations.GeneratePictures;
import guru.qa.rococo.jupiter.annotations.GenerateUser;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.page.MuseumPage;
import guru.qa.rococo.util.Utils;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.rococo.util.FakerUtils.generateNewMuseumTitle;
import static guru.qa.rococo.util.FakerUtils.generateRandomCity;
import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static guru.qa.rococo.util.FakerUtils.generateRandomSentence;

public class MuseumsTests extends BaseWebTest {


    private Utils utils = new Utils();

    @GenerateMuseum
            (
                    count = 2,
                    generatePictures = @GeneratePictures
                            (
                                    count = 3
                            )
            )
    @AllureId("7")
    @DisplayName("[WEB] [MUSEUM] Museums are available for unauthorized user")
    @Test
    void unauthorizedUserShouldSeeMuseums(@GenerateMuseum List<PaintingJson> paintingJsons) {
        List<MuseumJson> museumJsons = paintingJsons.stream().map(PaintingJson::getMuseum).distinct().toList();
        open(MuseumPage.URL, MuseumPage.class)
                .waitForPageLoaded()
                .checkAddMuseumButton(false)
                .checkMuseums(museumJsons);
    }

    @ApiLogin(user = @GenerateUser())
    @GenerateMuseum
            (
                    count = 3
            )
    @AllureId("8")
    @DisplayName("[WEB] [MUSEUM] Museums are available for authorized user")
    @Test
    void authorizedUserShouldSeeMuseums(@GenerateMuseum List<PaintingJson> paintingJsons) {
        List<MuseumJson> museumJsons = paintingJsons.stream().map(PaintingJson::getMuseum).distinct().toList();
        open(MuseumPage.URL, MuseumPage.class)
                .waitForPageLoaded()
                .checkAddMuseumButton(true)
                .checkMuseums(museumJsons);
    }

    @ApiLogin(user = @GenerateUser())
    @AllureId("9")
    @DisplayName("[WEB] [MUSEUM] An authorized user can add museums")
    @Test
    void authorizedUserCanAddMuseum() {
        String city = generateRandomCity();
        MuseumJson museumJson = new MuseumJson()
                .setTitle(generateNewMuseumTitle())
                .setDescription(generateRandomSentence(generateRandomInt(3, 7)))
                .setCity(city)
                .setGeo(utils.getRandomGeo(city, 0, 19));

        open(MuseumPage.URL, MuseumPage.class)
                .waitForPageLoaded()
                .addMuseum(
                        museumJson.getTitle(),
                        museumJson.getDescription(),
                        museumJson.getGeo().getCountry().getName(),
                        museumJson.getGeo().getCity(),
                        Utils.getRandomFileFromDir(IMAGES_DIR))
                .checkMuseum(museumJson);
    }
}
