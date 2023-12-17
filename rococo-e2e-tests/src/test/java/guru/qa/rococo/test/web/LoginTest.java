package guru.qa.rococo.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.data.entity.auth.AuthUserEntity;
import guru.qa.rococo.jupiter.annotations.AddUserToDB;
import guru.qa.rococo.page.LoginPage;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.WelcomePage;
import io.qameta.allure.AllureId;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags({@Tag("WEB"), @Tag("LOGIN")})
@Epic("LOGIN")
public class LoginTest extends BaseWebTest {

    @AddUserToDB
    @DisplayName("[WEB] [Login] User should login with valid credentials")
    @Test
    @AllureId("1")
    void loginTest(AuthUserEntity user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .waitForPageLoaded()
                .doLogin()
                .waitForPageLoaded()
                .fillLoginPage(user.getUsername(), user.getPassword())
                .submit(new MainPage())
                .waitForPageLoaded()
                .avatarShouldBeVisibleAfterLogin();
    }


    @AddUserToDB
    @DisplayName("[WEB] [Login] User should not login with incorrect credentials")
    @Test
    @AllureId("2")
    void incorrectUserLoginTest(AuthUserEntity user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .waitForPageLoaded()
                .doLogin()
                .waitForPageLoaded()
                .fillLoginPage(user.getUsername(), "wrongPassword")
                .submit(new LoginPage())
                .checkError("Неверные учетные данные пользователя");
    }
}
