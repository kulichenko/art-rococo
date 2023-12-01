package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

    public static final String URL = CFG.authUrl() + "login";

    SelenideElement
            header = $(".form__header"),
            usernameInput = $("input[name='username']"),
            passwordInput = $("input[name='password']"),
            submitButton = $(".form__submit"),
            errorContainer = $(".form__error");

    @Step("Fill login page with credentials: username: {0}, password: {1}")
    public LoginPage fillLoginPage(String login, String password) {
        setUsername(login);
        setPassword(password);
        return this;
    }

    @Step("Set username: {0}")
    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Set password: {0}")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Submit login")
    public <T extends BasePage> T submit(T expectedPage) {
        submitButton.click();
        return expectedPage;
    }

    @Step("Check error on page: {error}")
    public LoginPage checkError(String error) {
        errorContainer.shouldHave(text(error));
        return this;
    }

    @Override
    public LoginPage waitForPageLoaded() {
        header.shouldHave(text("Ro"));
        header.$(".form__text_gold").shouldHave(text("coco"));
        usernameInput.should(visible);
        passwordInput.should(visible);
        submitButton.should(visible).shouldHave(text("Войти"));

        return this;
    }
}
