package guru.qa.rococo.test;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.RegisterPage;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static guru.qa.rococo.util.FakerUtils.generateRandomUsername;

public class RegisterTest extends BaseWebTest {

    private final String basePassword = "12345";
    private final String secondBasePassword = "secondBasePassword";

    @Test
    @AllureId("3")
    @DisplayName("[WEB] [Registration] User should login after registration")
    void userShouldLoginAfterRegistering() {
        String username = generateRandomUsername();
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .waitForPageLoaded()
                .fillRegisterPage(username, basePassword)
                .successSubmit()
                .waitForPageLoaded()
                .fillLoginPage(username, basePassword)
                .submit(new MainPage())
                .waitForPageLoaded()
                .avatarShouldBeVisibleAfterLogin();
    }

    @Test
    @AllureId("4")
    @DisplayName("[WEB] [Registration] Registration should not be completed due to a different password")
    void registrationShouldNotBeCompletedDueToDifferentPassword() {
        String username = generateRandomUsername();
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .waitForPageLoaded()
                .setUsername(username)
                .setPassword(basePassword)
                .setPasswordSubmit(secondBasePassword)
                .errorSubmit()
                .checkErrorMessage("Passwords should be equal");
    }
}
