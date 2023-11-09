package guru.qa.rococo.controller;

import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NameNotFoundException;

@RestController
public class UserController {

    private final UserDataService userDataService;

    @Autowired
    public UserController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }


    @GetMapping("/user")
    public UserJson currentUser(@RequestParam String username) throws NameNotFoundException {
        return userDataService.getCurrentUser(username);
    }

    @PatchMapping("/user")
    public UserJson editUser (@RequestBody UserJson user) {
        return userDataService.editUser(user);
    }
}
