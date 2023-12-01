package guru.qa.rococo.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.config.Config;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage<T extends BasePage> {

    protected static final Config CFG = Config.getInstance();

    private final SelenideElement alertMessage = $(".text-base");

    public abstract T waitForPageLoaded();

    @Step("Check that success message appears: {expectedText}")
    @SuppressWarnings("unchecked")
    public T checkAlertMessage(String expectedText) {
        alertMessage.should(Condition.visible).should(Condition.text(expectedText));
        return (T) this;
    }
}
