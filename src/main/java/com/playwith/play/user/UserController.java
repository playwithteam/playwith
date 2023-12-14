package com.playwith.play.user;

import com.playwith.play.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static org.springframework.data.repository.util.ReactiveWrapperConverters.map;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Rq rq;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findUsername")
    public String FindUsername() {
        return "user_findUsername";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/findUsername")
    public String findUsername(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

        this.userService.findByName(userCreateForm.getName());
        this.userService.findByEmail(userCreateForm.getEmail());

        return userService.findByEmail(userCreateForm.getEmail())
                .map(siteUser ->
                        rq.redirect(
                                "/user/login",""
                        )
                )
                .orElse(rq.redirect("/user/findUsername", "아이디가존재하지않습니다."));
    }

}