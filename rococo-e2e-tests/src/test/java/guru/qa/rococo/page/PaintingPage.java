package guru.qa.rococo.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.page.components.AddPaintingModal;
import io.qameta.allure.Step;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaintingPage extends BasePage<PaintingPage> {

    public static final String URL = CFG.baseUrl() + "/painting";

    private final SelenideElement pageTitle = $$("h2").find(Condition.text("Картины"));

    private final SelenideElement addPaintingBtn = $$("button").find(Condition.text("Добавить картину"));

    private final AddPaintingModal addPaintingModal = new AddPaintingModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));

    private final SelenideElement paintingsGrid = $(".grid.grid-cols-1.gap-4");
    private final ElementsCollection paintingsCards = $$("a[href*='/painting/']");

    @Override
    public PaintingPage waitForPageLoaded() {
        progressRadialShouldNotBeVisible();
        pageTitle.should(visible);
        searchInput.should(visible);
        searchSubmitBtn.should(visible);
        return this;
    }

    @Step("Add painting")
    public PaintingPage addPainting(PaintingJson paintingJson, File image) {
        addPaintingBtn.click();
        addPaintingModal.fillPaintingInfo(paintingJson, image);
        addPaintingModal.submit(new PaintingPage());
        return this;
    }

    @Step("Check button 'add painting'")
    public PaintingPage checkAddPaintingButton(boolean isAvailable) {
        if (isAvailable) {
            addPaintingBtn.should(visible);
        } else {
            addPaintingBtn.shouldNotBe(visible);
        }
        return this;
    }


    @Step("Check that paintings are enabled")
    public PaintingPage checkPaintings(List<PaintingJson> paintingJsons) {
        paintingsCards.should(sizeGreaterThanOrEqual(paintingJsons.size()));
        for (var painting : paintingJsons) {
            String title = painting.getTitle();
            searchInput.setValue(title);
            searchSubmitBtn.click();
            paintingsCards.find(text(title)).should(visible);
        }
        return this;
    }

    @Step("Check that painting is enabled")
    public PaintingPage checkPainting(PaintingJson paintingJson) {
        String title = paintingJson.getTitle();
        searchInput.setValue(title);
        searchSubmitBtn.click();
        paintingsCards.find(text(title)).should(visible);
        return this;
    }

}
