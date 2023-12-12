package guru.qa.rococo.page.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.MuseumPage;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import java.io.File;

public class AddMuseumModal extends BaseComponent {

    private final SelenideElement title = self.$("input[name='title'");
    private final SelenideElement description = self.$("textarea[name='description'");
    private final SelenideElement countrySelect = self.$("select[name='countryId']");
    private final SelenideElement cityInput = self.$("input[name='city']");
    private final SelenideElement updatePhotoInput = self.$("input[name='photo']");
    private final SelenideElement closeBtn = self.$(".btn.variant-ringed");
    private final SelenideElement submitBtn = self.$("button[type='submit']");
    private final SelenideElement museumImage = self.$(".max-w-full"); //только при редактировании музея


    public AddMuseumModal(@Nonnull SelenideElement self) {
        super(self);
    }

    @Step("Fill museum card")
    public AddMuseumModal fillMuseumInformation(String title, String description, String country, String city, File imageFile) {
        this.title.setValue(title);
        this.description.setValue(description);
        countrySelect.$$("option").find(Condition.text(country)).scrollTo().click();
        cityInput.setValue(city);
        updatePhotoInput.uploadFile(imageFile);
        return this;
    }

    @Step("Submit new museum")
    public MuseumPage submitMuseum() {
        submitBtn.click();
        return new MuseumPage();
    }

}
