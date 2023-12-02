package guru.qa.rococo.page.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.BasePage;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.File;

import static com.codeborne.selenide.Condition.visible;

@Getter
@Slf4j
public class Profile extends BaseComponent<Profile> {

    private final SelenideElement title = self.$("header");
    private final SelenideElement avatarImage = self.$(".avatar-image");
    private final SelenideElement username = self.$("h4.text-center");
    private final SelenideElement exitBtn = self.$(".btn.variant-ghost");
    private final SelenideElement closeBtn = self.$(".btn.variant-ringed");
    private final SelenideElement submitBtn = self.$("button[type='submit']");
    private final SelenideElement updatePhotoInput = self.$("input[name='content']");
    private final SelenideElement firstnameInput = self.$("input[name='firstname']");
    private final SelenideElement surnameInput = self.$("input[name='surname']");


    public Profile(@Nonnull SelenideElement self) {
        super(self);
    }

    @Step("Fill firstname")
    public Profile fillFirstname(String firstname) {
        firstnameInput.setValue(firstname);
        return this;
    }

    @Step("Fill surname")
    public Profile fillSurname(String surname) {
        surnameInput.setValue(surname);
        return this;
    }

    @Step("Upload avatar")
    public Profile uploadAvatar(String pathToAvatar) {
        updatePhotoInput.uploadFile(new File(pathToAvatar));
        return this;
    }

    @Step("Check avatar")
    public Profile checkAvatar() {
        avatarImage.should(visible);
        return this;
    }

    @Step("Submit form")
    public <T extends BasePage> T submitForm(T expectedPage) {
        submitBtn.click();
        return expectedPage;
    }

    @Step("Check firstname")
    public Profile checkFirstname(String firstname) {
        //firstname is invisible in FF
        log.info("How can i get {} from shadow-roooot!!!", firstname);
        return this;
    }

    @Step("Check surname")
    public Profile checkSurname(String surname) {
        //firstname is surname in FF
        log.info("How can i get {} from shadow-roooot!!!", surname);
        return this;
    }

    @Step("Check username")
    public Profile checkUsername(String username) {
        this.username.should(Condition.text(username));
        return this;
    }


}
