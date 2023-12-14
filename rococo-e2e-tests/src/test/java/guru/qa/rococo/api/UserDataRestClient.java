package guru.qa.rococo.api;

import guru.qa.rococo.UserdataApi;
import guru.qa.rococo.model.UserJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UserDataRestClient extends RestService {
    public UserDataRestClient(@Nonnull String baseUrl) {
        super(CFG.userDataUrl());
    }

    private final UserdataApi userdataApi = retrofit.create(UserdataApi.class);

    @Step("Send REST GET('/currentUser') request to userdata service")
    @Nullable
    public UserJson getCurrentUser(@Nonnull String username) throws Exception {
        return userdataApi.currentUser(username)
                .execute()
                .body();
    }

    @Step("Send REST POST('/editUser') request to userdata service")
    @Nullable
    public UserJson updateUser(@Nonnull UserJson userJson) throws Exception {
        return userdataApi.editUser(userJson)
                .execute()
                .body();
    }
}
