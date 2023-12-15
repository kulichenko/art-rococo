package guru.qa.rococo.api;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.rococo.api.context.CookieContext;
import guru.qa.rococo.api.context.LocalStorageContext;
import guru.qa.rococo.api.interceptor.AddCookieInterceptor;
import guru.qa.rococo.api.interceptor.RecievedCodeInterceptor;
import guru.qa.rococo.api.interceptor.RecievedCookieInterceptor;
import io.qameta.allure.Step;

import java.io.IOException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AuthServiceClient extends RestService {
    private final AuthService authService = retrofit.create(AuthService.class);

    public AuthServiceClient() {
        super(CFG.authUrl(), true,
                new RecievedCookieInterceptor(),
                new AddCookieInterceptor(),
                new RecievedCodeInterceptor()
        );
    }

    @Step("Do api login")
    public void doLogin(String username, String password) throws IOException {
        LocalStorageContext localStorageContext = LocalStorageContext.getInstance();
        CookieContext cookieContext = CookieContext.getInstance();
        authService.authorize(
                "code",
                "client",
                "openid",
                CFG.frontUrl() + "/authorized",
                localStorageContext.getCodeChallenge(),
                "S256"
        ).execute();

        authService.login(
                username,
                password,
                cookieContext.getXsrfTokenCookieValue()
        ).execute();

        JsonNode response = authService.token(
                "Basic " + new String(Base64.getEncoder().encode("client:secret".getBytes(UTF_8))),
                "client",
                CFG.frontUrl() + "/authorized",
                "authorization_code",
                localStorageContext.getCode(),
                localStorageContext.getCodeVerifier()
        ).execute().body();

        localStorageContext.setToken(response.get("id_token").asText());
    }
}