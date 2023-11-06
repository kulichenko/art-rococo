package guru.qa.rococo.service;


import guru.qa.rococo.model.UserJson;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserDataClient {

    @Nonnull
    UserJson currentUser(@Nonnull String username);

    @Nonnull
    UserJson editUser(@Nonnull @RequestBody UserJson user);
}
