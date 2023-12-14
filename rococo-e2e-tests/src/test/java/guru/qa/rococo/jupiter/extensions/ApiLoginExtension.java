package guru.qa.rococo.jupiter.extensions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.rococo.api.AuthServiceClient;
import guru.qa.rococo.api.context.CookieContext;
import guru.qa.rococo.api.context.LocalStorageContext;
import guru.qa.rococo.config.Config;
import guru.qa.rococo.jupiter.annotations.ApiLogin;
import guru.qa.rococo.jupiter.annotations.GenerateUser;
import guru.qa.rococo.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import java.io.IOException;

import static guru.qa.rococo.jupiter.extensions.CreateUserExtension.NESTED;


public class ApiLoginExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    private final AuthServiceClient authServiceClient = new AuthServiceClient();
    private final boolean setUpBrowser;

    public ApiLoginExtension() {
        this.setUpBrowser = true;
    }

    public ApiLoginExtension(boolean setUpBrowser) {
        this.setUpBrowser = setUpBrowser;
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        ApiLogin annotation = extensionContext.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (annotation != null) {
            GenerateUser user = annotation.user();
            if (user.handleAnnotation()) {
                UserJson createdUser = extensionContext.getStore(NESTED).get(
                        getAllureId(extensionContext),
                        UserJson.class
                );
                doLogin(createdUser.getUsername(), createdUser.getPassword());
            } else {
                doLogin(annotation.username(), annotation.password());
            }
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        LocalStorageContext.getInstance().clearContext();
        CookieContext.getInstance().clearContext();
    }

    private void doLogin(String username, String password) {
        LocalStorageContext localStorageContext = LocalStorageContext.getInstance();
        localStorageContext.init();

        try {
            authServiceClient.doLogin(username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Selenide.open(Config.getInstance().baseUrl());
        Selenide.localStorage().setItem("codeChallenge", localStorageContext.getCodeChallenge());
        Selenide.localStorage().setItem("id_token", localStorageContext.getToken());
        Selenide.localStorage().setItem("codeVerifier", localStorageContext.getCodeVerifier());
        Cookie jsessionIdCookie = new Cookie("JSESSIONID", CookieContext.getInstance().getJSessionIdCookieValue());
        WebDriverRunner.getWebDriver().manage().addCookie(jsessionIdCookie);
    }

    public static ApiLoginExtension create(boolean setUpBrowser) {
        return new ApiLoginExtension(setUpBrowser);
    }

    private String getAllureId(ExtensionContext context) {
        var allureId = context.getRequiredTestMethod().getAnnotation(AllureId.class);
        if (allureId == null) {
            throw new IllegalStateException("Annotation @AllureId must be present!");
        }
        return allureId.value();
    }
}