package guru.qa.rococo.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.config.Config;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage<T extends BasePage> {

    protected static final Config CFG = Config.getInstance();

    private final SelenideElement alertMessage = $(".text-base");
    public final SelenideElement progressRadial = $("figure[data-testid='progress-radial']");

    @Step("Checking that page is loaded")
    public abstract T waitForPageLoaded();

    @Step("Check that success message appears: {expectedText}")
    @SuppressWarnings("unchecked")
    public T checkAlertMessage(String expectedText) {
        alertMessage.should(Condition.visible).should(Condition.text(expectedText));
        return (T) this;
    }

    public void progressRadialShouldNotBeVisible() {
        progressRadial.shouldNotBe(visible, Duration.ofSeconds(6000));
    }
}
