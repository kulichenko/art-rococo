package guru.qa.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class WelcomePage extends BasePage<WelcomePage> {


    public static final String URL = CFG.frontUrl();

    private final ElementsCollection titles = $$(".text-center.text-2xl.p-6.text-bold");
    private final SelenideElement
            loginButton = $$("button").find(text("Войти")),
            paintingTitle = titles.find(text("Картины")),
            artistTitle = titles.find(text("Художники")),
            museumTitle = titles.find(text("Музеи"));

    @Step("Redirect to login page")
    public LoginPage doLogin() {
        loginButton.click();
        return new LoginPage();
    }


    @Step("Check that page is loaded")
    @Override
    public WelcomePage waitForPageLoaded() {
        loginButton.should(visible);
        paintingTitle.should(visible);
        artistTitle.should(visible);
        museumTitle.should(visible);
        return this;
    }
}
