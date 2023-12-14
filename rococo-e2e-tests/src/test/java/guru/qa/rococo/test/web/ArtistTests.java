package guru.qa.rococo.test.web;

import guru.qa.rococo.jupiter.annotations.ApiLogin;
import guru.qa.rococo.jupiter.annotations.GenerateMuseum;
import guru.qa.rococo.jupiter.annotations.GeneratePictures;
import guru.qa.rococo.jupiter.annotations.GenerateUser;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.page.ArtistPage;
import guru.qa.rococo.page.ArtistProfilePage;
import guru.qa.rococo.util.FakerUtils;
import guru.qa.rococo.util.Utils;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static guru.qa.rococo.util.FakerUtils.generateRandomName;
import static guru.qa.rococo.util.FakerUtils.generateRandomSentence;
import static guru.qa.rococo.util.Utils.getRandomFileFromDir;

public class ArtistTests extends BaseWebTest {
    //paintingShouldBeEnabledOnArtistProfilePage
    @GenerateMuseum(generatePictures = @GeneratePictures(count = 2))
    @AllureId("10")
    @DisplayName("[WEB] [ARTIST] Artists are available for unauthorized user")
    @Test
    void artistShouldBeVisibleOnArtistPage(@GenerateMuseum List<PaintingJson> paintingJsons) {
        List<ArtistJson> artistJsons = paintingJsons.stream().map(PaintingJson::getArtist).distinct().toList();
        open(ArtistPage.URL, ArtistPage.class)
                .waitForPageLoaded()
                .searchArtist(artistJsons);
    }


    @GenerateMuseum(generatePictures = @GeneratePictures)
    @ApiLogin(user = @GenerateUser())
    @AllureId("11")
    @DisplayName("[WEB] [ARTIST] Trying adding painting from artist profile page")
    @Test
    void paintingShouldBeVisibleOnArtistProfilePageAfterAddingPaintingFromArtistProfilePage(@GenerateMuseum List<PaintingJson> paintingJsons) {
        List<ArtistJson> artistJsons = paintingJsons.stream().map(PaintingJson::getArtist).distinct().toList();
        PaintingJson paintingJson = new PaintingJson();
        paintingJson.setTitle(generateRandomSentence(generateRandomInt(1, 3)));
        paintingJson.setDescription(generateRandomSentence(generateRandomInt(3, 5)));
        open(ArtistPage.URL, ArtistPage.class)
                .waitForPageLoaded()
                .selectArtist(artistJsons.get(0).getName())
                .waitForPageLoaded()
                .clickAddPaintingBtn()
                .fillPaintingInfo(paintingJson, getRandomFileFromDir(IMAGES_DIR))
                .submit(new ArtistProfilePage())
                .waitForPageLoaded()
                .checkThatPaintingIsEnableOnArtistProfilePage(paintingJson);
    }

    @GenerateMuseum(generatePictures = @GeneratePictures)
    @AllureId("12")
    @DisplayName("[WEB] [ARTIST] Unauthorized user can't add artists")
    @Test
    void unauthorizedUserCanNotAddArtist() {
        open(ArtistPage.URL, ArtistPage.class)
                .waitForPageLoaded()
                .checkButtonAddArtist(new ArtistPage(), false);
    }

    @ApiLogin(user = @GenerateUser)
    @AllureId("13")
    @DisplayName("[WEB] [ARTIST] Authorized user can add artists from Artist page")
    @Test
    void authorizedUserCanAddArtist() {
        ArtistJson artistJson = new ArtistJson();
        artistJson.setName(generateRandomName());
        artistJson.setBiography(FakerUtils.generateRandomSentence(generateRandomInt(3, 7)));
        open(ArtistPage.URL, ArtistPage.class)
                .waitForPageLoaded()
                .addArtist()
                .fillArtistInfo(artistJson, Utils.getRandomFileFromDir(IMAGES_DIR))
                .submit(new ArtistPage())
                .selectArtist(artistJson.getName())
                .waitForPageLoaded()
                .checkArtistInfo(artistJson);
    }
}
