package guru.qa.rococo.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.page.components.AddArtistModal;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ArtistPage extends BasePage<ArtistPage> {
    public static final String URL = CFG.baseUrl() + "/artist";

    private final SelenideElement addArtistBtn = $$("button").find(Condition.text("Добавить художника"));
    private final SelenideElement pageTitle = $$("h2").find(Condition.text("Художники"));
    private final ElementsCollection artistsCards = $$(".flex.flex-col.justify-center.items-center");

    @Override
    public ArtistPage waitForPageLoaded() {
        progressRadialShouldNotBeVisible();
        pageTitle.should(visible);
        searchInput.should(visible);
        searchSubmitBtn.should(visible);
        return this;
    }

    @Step("Check button 'Add artist")
    public <T extends BasePage> T checkButtonAddArtist(T expectedPage, boolean isAvailable) {
        if (isAvailable) {
            addArtistBtn.should(visible);
        } else {
            addArtistBtn.shouldNotBe(visible);
        }
        return expectedPage;
    }

    @Step("Add artist")
    public AddArtistModal addArtist() {
        addArtistBtn.click();
        return new AddArtistModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));
    }

    @Step("Select artist")
    public ArtistProfilePage selectArtist(String artistName) {
        searchInput.setValue(artistName);
        searchSubmitBtn.click();
        artistsCards.find(text(artistName)).click();
        progressRadialShouldNotBeVisible();
        return new ArtistProfilePage();
    }

    @Step("Search artist")
    public ArtistPage searchArtist(List<ArtistJson> artistJsons) {
        for (var artist : artistJsons) {
            searchInput.setValue(artist.getName());
            searchSubmitBtn.click();
            artistsCards.find(text(artist.getName())).should(visible);
            progressRadialShouldNotBeVisible();
        }
        return this;
    }

}
