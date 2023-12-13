package com.playwith.play.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/id_search")
    public String id_search() {
        return "id_search";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/id_search_result")
    public String id_search_result() {
        return "id_search_result";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/password_search")
    public String password_result() {
        return "password_search";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/password_search_result")
    public String password_search_result() {
        return "password_search_result";
    }
}
