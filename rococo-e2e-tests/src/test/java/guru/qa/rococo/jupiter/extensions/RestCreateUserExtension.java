package guru.qa.rococo.jupiter.extensions;

import guru.qa.rococo.api.UserService;
import guru.qa.rococo.config.Config;
import guru.qa.rococo.jupiter.annotations.GenerateUser;
import guru.qa.rococo.model.UserJson;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static guru.qa.rococo.util.FakerUtils.generateRandomName;


public class RestCreateUserExtension extends CreateUserExtension {
    protected static final Config CFG = Config.getInstance();
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static final Retrofit userRetrofit = new Retrofit.Builder()
            .client(httpClient)
            .baseUrl(CFG.authUrl())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    private static final UserService userService = userRetrofit.create(UserService.class);
    private static final String DEFAULT_PASSWORD = "12345";

    @Override
    protected UserJson createUserForTest(GenerateUser annotation) throws IOException {
        var authUser = new UserJson();
        authUser.setUsername(generateRandomName());
        authUser.setPassword(DEFAULT_PASSWORD);
        var xsrfToken = getCsrf();
        userService.register
                (
                        "XSRF-TOKEN=" + xsrfToken,
                        xsrfToken,
                        authUser.getUsername(),
                        authUser.getPassword(),
                        authUser.getPassword()
                ).execute();

        return authUser;
    }

    private String getCsrf() throws IOException {
        var execute = userService.register().execute();
        return execute.headers().get("X-XSRF-TOKEN");
    }
}
