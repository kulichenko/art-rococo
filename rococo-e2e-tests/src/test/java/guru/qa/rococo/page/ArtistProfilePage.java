package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.page.components.AddPaintingModal;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ArtistProfilePage extends BasePage<ArtistProfilePage> {

    private final SelenideElement artistName = $(".card-header");
    private final SelenideElement biography = $(".col-span-2.m-2");
    private final SelenideElement avatarImage = $(".avatar-image");
    private final SelenideElement artistPictures = $(".grid.grid-cols-1.gap-4");
    private final SelenideElement emptyPicturesListMessage = $(new ByText("Пока что список картин этого художника пуст."));

    //for authorized user only
    private final SelenideElement editArtistBtn = $("button[data-testid='edit-artist']");
    private final SelenideElement addPictureMainBtn = $(".btn.variant-filled-primary.m-3");
    private final SelenideElement addPictureSecondBtn = $(".btn.variant-filled-primary.ml-4");


    @Override
    public ArtistProfilePage waitForPageLoaded() {
        progressRadialShouldNotBeVisible();
        artistName.should(visible, Duration.ofSeconds(3000));
        biography.should(visible, Duration.ofSeconds(3000));
        return this;
    }

    @Step("Add picture")
    public AddPaintingModal clickAddPaintingBtn() {
        addPictureMainBtn.click();
        return new AddPaintingModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));
    }

    @Step("Check that painting is enable on artist profile page")
    public ArtistProfilePage checkThatPaintingIsEnableOnArtistProfilePage(PaintingJson paintingJson) {
        artistPictures.$$(".text-center").find(text(paintingJson.getTitle())).should(visible);
        return this;
    }

    @Step("Check artist's information")
    public ArtistProfilePage checkArtistInfo(ArtistJson artistJson) {
        artistName.should(text(artistJson.getName()));
        biography.should(text(artistJson.getBiography()));
        return this;
    }
}
