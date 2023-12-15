package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.page.components.Header;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class MainPage extends BasePage<MainPage> {

    public static final String URL = CFG.frontUrl();

    private final Header header = new Header($("#shell-header"));
    private final SelenideElement welcomeTitle = $(".text-3xl.text-center.m-14");
    private final SelenideElement paintingCard = $("a[href='/painting']");
    private final SelenideElement artistsCard = $("a[href='/artist']");
    private final SelenideElement museumsCard = $("a[href='/museum']");

    @Override
    public MainPage waitForPageLoaded() {
        welcomeTitle.should(text("Ваши любимые картины и художники всегда рядом"));
        paintingCard.should(visible);
        artistsCard.should(visible);
        museumsCard.should(visible);
        return this;
    }

    @Step("Check avatar visibility")
    public MainPage avatarShouldBeVisibleAfterLogin() {
        header.getAvatar().should(visible);
        return this;
    }
}
