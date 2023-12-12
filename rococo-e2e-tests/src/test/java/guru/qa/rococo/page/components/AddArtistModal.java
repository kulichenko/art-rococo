package guru.qa.rococo.page.components;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.page.BasePage;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class AddArtistModal extends BaseComponent {

    private final SelenideElement title = self.$("header");
    private final SelenideElement artistNameInput = self.$("input[name='name']");
    private final SelenideElement updatePhotoInput = self.$("input[name='photo']");
    private final SelenideElement biographyInput = self.$("textarea[name='biography']");
    private final SelenideElement closeBtn = self.$(".btn.variant-ringed");
    private final SelenideElement submitBtn = self.$("button[type='submit']");
    private final SelenideElement avatarImage = self.$(".avatar-image"); //только при редактировании художника


    public AddArtistModal(@Nonnull SelenideElement self) {
        super(self);
    }

    @Step("Wait for modal loaded")
    public AddArtistModal waitForModalLoaded() {
        title.should(text("Новый художник"), Duration.ofSeconds(2000));
        closeBtn.should(visible);
        submitBtn.should(visible);
        return this;
    }

    @Step("Fill artist information to modal")
    public AddArtistModal fillArtistInfo(ArtistJson artistJson, File file) {
        artistNameInput.setValue(artistJson.getName());
        biographyInput.setValue(artistJson.getBiography());
        updatePhotoInput.uploadFile(file);
        return this;
    }

    @Step("Submit add artist")
    public <T extends BasePage> T submit(T expectedPage) {
        submitBtn.click();
        return expectedPage;
    }

}
