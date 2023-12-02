package guru.qa.rococo.page.components;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.ArtistPage;
import guru.qa.rococo.page.MuseumPage;
import guru.qa.rococo.page.PaintingPage;
import io.qameta.allure.Step;
import lombok.Getter;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class Header extends BaseComponent<Header> {

    private final SelenideElement paintingButton = self.$("a[href*='/painting']");
    private final SelenideElement artistButton = self.$("a[href*='/artist']");
    private final SelenideElement museumButton = self.$("a[href*='/museum']");
    private final SelenideElement loginButton = self.$$("button").find(text("Войти"));
    private final SelenideElement avatar = self.$(".avatar");

    public Header(@Nonnull SelenideElement self) {
        super(self);
    }

    @Step("Go to Painting page")
    @Nonnull
    public PaintingPage goToPaintingPage() {
        paintingButton.click();
        return new PaintingPage();
    }


    @Step("Go to Artist page")
    @Nonnull
    public ArtistPage goToArtistPage() {
        artistButton.click();
        return new ArtistPage();
    }

    @Step("Go to Museum page")
    @Nonnull
    public MuseumPage goToMuseumPage() {
        museumButton.click();
        return new MuseumPage();
    }


    @Step("Go to Profile modal window")
    @Nonnull
    public Profile goToProfile() {
        avatar.click();
        return new Profile($(".card.p-4.w-modal.shadow-xl.space-y-4"));
    }

}
