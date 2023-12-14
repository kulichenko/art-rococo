package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotations.ApiLogin;
import guru.qa.rococo.jupiter.annotations.GenerateMuseum;
import guru.qa.rococo.jupiter.annotations.GeneratePictures;
import guru.qa.rococo.jupiter.annotations.GenerateUser;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.page.PaintingPage;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static guru.qa.rococo.util.FakerUtils.generateRandomSentence;
import static guru.qa.rococo.util.Utils.getRandomFileFromDir;

public class PaintingTests extends BaseWebTest {
    @GenerateMuseum
            (
                    generatePictures = @GeneratePictures
                            (
                                    count = 3
                            )
            )
    @AllureId("14")
    @DisplayName("[WEB] [PAINTING] Paintings are available for unauthorized user")
    @Test
    void unauthorizedUserShouldSeePaintings(@GenerateMuseum List<PaintingJson> paintingJsons) {
        open(PaintingPage.URL, PaintingPage.class)
                .waitForPageLoaded()
                .checkAddPaintingButton(false)
                .checkPaintings(paintingJsons);
    }

    @ApiLogin(user = @GenerateUser())
    @GenerateMuseum
            (
                    generatePictures = @GeneratePictures(
                            count = 2
                    )
            )
    @AllureId("15")
    @DisplayName("[WEB] [PAINTING] Paintings are available for authorized user")
    @Test
    void authorizedUserShouldSeePaintings(@GenerateMuseum List<PaintingJson> paintingJsons) {
        open(PaintingPage.URL, PaintingPage.class)
                .waitForPageLoaded()
                .checkAddPaintingButton(true)
                .checkPaintings(paintingJsons);
    }

    @ApiLogin(user = @GenerateUser())
    @AllureId("16")
    @DisplayName("[WEB] [PAINTING] An authorized user can add painting")
    @Test
    void authorizedUserCanAddPainting() {
        PaintingJson paintingJson = new PaintingJson()
                .setTitle(generateRandomSentence(generateRandomInt(1, 3)))
                .setDescription(generateRandomSentence(generateRandomInt(3, 7)));

        open(PaintingPage.URL, PaintingPage.class)
                .waitForPageLoaded()
                .addPainting(paintingJson, getRandomFileFromDir(IMAGES_DIR))
                .checkPainting(paintingJson);
    }
}
