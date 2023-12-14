package com.playwith.play.domain.user.controller;

import com.playwith.play.global.rq.Rq;
import com.playwith.play.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Rq rq;

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
    @PostMapping("/id_search")
    public String id_search(String email, String name) {
        this.userService.findByName(email);
        this.userService.findByEmail(name);

        return userService.findByEmail(email)
                .map(siteUser ->
                        rq.redirect(
                                "/user/login", ""
                        )
                )
                .orElse(rq.redirect("/user/id_search", "아이디가존재하지않습니다."));
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