package guru.qa.rococo.test;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.data.entity.auth.AuthUserEntity;
import guru.qa.rococo.jupiter.annotations.AddUserToDB;
import guru.qa.rococo.page.LoginPage;
import guru.qa.rococo.page.MainPage;
import guru.qa.rococo.page.WelcomePage;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class LoginTest extends BaseWebTest {

    @AddUserToDB
    @DisplayName("[WEB] [Login] User should login with valid credentials")
    @Test
    @AllureId("1")
    void loginTest(AuthUserEntity user) {
        Selenide.open(CFG.baseUrl(), WelcomePage.class)
                .waitForPageLoaded()
                .doLogin()
                .waitForPageLoaded()
                .fillLoginPage(user.getUsername(), user.getPassword())
                .submit(new MainPage())
                .waitForPageLoaded();
    }


    @AddUserToDB
    @DisplayName("[WEB] [Login] User should not login with incorrect credentials")
    @Test
    @AllureId("2")
    void incorrectUserLoginTest(AuthUserEntity user) {
        Selenide.open(CFG.baseUrl(), WelcomePage.class)
                .waitForPageLoaded()
                .doLogin()
                .waitForPageLoaded()
                .fillLoginPage(user.getUsername(), "wrongPassword")
                .submit(new LoginPage())
                .checkError("Неверные учетные данные пользователя");
    }
}
