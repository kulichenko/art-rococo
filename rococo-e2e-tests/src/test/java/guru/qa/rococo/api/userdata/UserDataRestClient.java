package guru.qa.rococo.api.userdata;

import guru.qa.rococo.api.RestService;
import guru.qa.rococo.model.UserJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UserDataRestClient extends RestService {
    public UserDataRestClient() {
        super(CFG.userDataUrl());
    }

    private final UserdataApi api = retrofit.create(UserdataApi.class);

    @Step("Send REST GET('/currentUser') request to userdata service")
    @Nullable
    public UserJson getCurrentUser(@Nonnull String username) throws Exception {
        return api.currentUser(username)
                .execute()
                .body();
    }

    @Step("Send REST PATCH('/editUser') request to userdata service")
    @Nullable
    public UserJson editUser(@Nonnull UserJson userJson) throws Exception {
        return api.editUser(userJson)
                .execute()
                .body();
    }
}
