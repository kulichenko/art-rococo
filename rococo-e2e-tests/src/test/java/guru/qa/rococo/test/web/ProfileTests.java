package guru.qa.rococo.test.web;

import guru.qa.rococo.jupiter.annotations.ApiLogin;
import guru.qa.rococo.jupiter.annotations.GenerateUser;
import guru.qa.rococo.jupiter.annotations.GeneratedUser;
import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.page.MainPage;
import io.qameta.allure.AllureId;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.rococo.util.FakerUtils.generateRandomName;
import static guru.qa.rococo.util.FakerUtils.generateRandomSurname;

@Tags({@Tag("WEB"), @Tag("PROFILE")})
@Epic("PROFILE")
public class ProfileTests extends BaseWebTest {

    @ApiLogin(user = @GenerateUser())
    @Test
    @AllureId("5")
    @DisplayName("[WEB] [PROFILE] Check user profile data after update")
    void updateAndCheckUserProfileData(@GeneratedUser UserJson userForTest) {
        var name = generateRandomName();
        var surname = generateRandomSurname();
        open(MainPage.URL, MainPage.class)
                .waitForPageLoaded()
                .getHeader()
                .goToProfile()
                .checkUsername(userForTest.getUsername())
                .uploadAvatar()
                .fillFirstname(name)
                .fillSurname(surname)
                .submitForm(new MainPage())
                .getHeader()
                .goToProfile()
                .checkAvatar()
                .checkFirstname(name)
                .checkSurname(surname);
    }

}
