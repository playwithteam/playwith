package com.playwith.play.domain.user.controller;

import com.playwith.play.domain.user.service.UserService;
import com.playwith.play.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup";
        }

        try {
            userService.join(userCreateForm.getProfileImgUrl(), userCreateForm.getUsername(), userCreateForm.getName(),
                    userCreateForm.getPassword1(), userCreateForm.getEmail(), userCreateForm.getArea(), userCreateForm.getLevel(), userCreateForm.getBirthDate());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }

        return "redirect:/";
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