package com.playwith.play.domain.user.controller;

import com.playwith.play.global.rq.Rq;
import com.playwith.play.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
    @ResponseBody
    public Map<String, Object> signup(@RequestBody @Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("success", false);
            response.put("errors", getErrorMessages(bindingResult));
            return response;
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            response.put("success", false);
            response.put("errors", getErrorMessages(bindingResult));
            return response;
        }

        userService.join(userCreateForm.getProfileImgUrl(), userCreateForm.getUsername(), userCreateForm.getName(),
                userCreateForm.getPassword1(), userCreateForm.getEmail(), userCreateForm.getArea(), userCreateForm.getLevel(), userCreateForm.getBirthDate());

        response.put("success", true);
        return response;
    }

    private Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
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