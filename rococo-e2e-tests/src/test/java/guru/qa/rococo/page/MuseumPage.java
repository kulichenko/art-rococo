package guru.qa.rococo.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.page.components.AddMuseumModal;
import io.qameta.allure.Step;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MuseumPage extends BasePage<MuseumPage> {

    public static final String URL = CFG.baseUrl() + "/museum";

    private final AddMuseumModal modal = new AddMuseumModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));

    private final SelenideElement addMuseumBtn = $$("button").find(Condition.text("Добавить музей"));
    private final SelenideElement museumPageTitle = $$("h2").find(Condition.text("Музеи"));
    private final SelenideElement searchMuseumInput = $("input[type='search']");
    private final SelenideElement searchSubmitBtn = $(".btn-icon.variant-soft-surface.ml-4");

    private final ElementsCollection museumsCards = $$("a[href*='/museum/']");


    @Override
    public MuseumPage waitForPageLoaded() {
        museumPageTitle.should(visible);
        searchMuseumInput.should(visible);
        searchSubmitBtn.should(visible);
        return this;
    }

    @Step("Add museum")
    public MuseumPage addMuseum(String title, String description, String country, String city, File image) {
        addMuseumBtn.click();
        modal.fillMuseumInformation(title, description, country, city, image);
        modal.submitMuseum();
        return this;
    }

    @Step("Check that museum is enable")
    public MuseumPage checkMuseums(List<MuseumJson> museumJsons) {
        museumsCards.should(CollectionCondition.sizeGreaterThanOrEqual(museumJsons.size()));
        for (var museum : museumJsons) {
            String title = museum.getTitle();
            String city = museum.getCity();
            String name = museum.getGeo().getCountry().getName();
            searchMuseumInput.setValue(title);
            searchSubmitBtn.click();
            museumsCards.find(text(title)).should(visible);
            museumsCards.find(text(city)).should(visible);
            museumsCards.find(text(name)).should(visible);
        }
        return this;
    }

    @Step("Check that museum is enable")
    public MuseumPage checkMuseum(MuseumJson museum) {
        String title = museum.getTitle();
        String city = museum.getCity();
        String name = museum.getGeo().getCountry().getName();
        searchMuseumInput.setValue(title);
        searchSubmitBtn.click();
        museumsCards.find(text(title)).should(visible);
        museumsCards.find(text(city)).should(visible);
        museumsCards.find(text(name)).should(visible);
        return this;
    }

    @Step("Check button 'add museum'")
    public MuseumPage checkAddMuseumButton(boolean isAvailable) {
        if (isAvailable) {
            addMuseumBtn.should(visible);
        } else {
            addMuseumBtn.shouldNotBe(visible);
        }
        return this;
    }
}
