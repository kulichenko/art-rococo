package guru.qa.rococo.controller;


import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserDataClient;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequestMapping("/api/user")
@RestController
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserDataClient userDataClient;

    @Autowired
    public UserController(@Qualifier("rest") UserDataClient userDataClient) {
        this.userDataClient = userDataClient;
    }

    @GetMapping()
    public UserJson currentUser(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userDataClient.currentUser(username);
    }

    @PatchMapping()
    public UserJson editUser(@Valid @RequestBody UserJson user,
                               @AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        LOG.info("Payload: {}", user.toString());
        UserJson userJson = user.addUsername(username);
        LOG.info("UserJson to webclient: {}", userJson);

        return userDataClient.editUser(userJson);
    }

}
