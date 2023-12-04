package guru.qa.rococo.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import guru.qa.rococo.config.Config;
import guru.qa.rococo.jupiter.annotations.DBTest;
import guru.qa.rococo.jupiter.annotations.WebTest;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;

@WebTest
@DBTest
public abstract class BaseWebTest {

    protected static final Config CFG = Config.getInstance();

    @BeforeEach
    void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(false)
                .savePageSource(false)
        );
    }
}
