package com.playwith.play.domain.user.controller;

import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.service.UserService;
import com.playwith.play.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Rq rq;
    private SiteUser findUser;

    //로그인
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
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

        // 아이디 중복
        try {
            userService.join(userCreateForm.getProfileImgUrl(), userCreateForm.getUsername(), userCreateForm.getName(),
                    userCreateForm.getPassword1(), userCreateForm.getEmail(), userCreateForm.getArea(), userCreateForm.getLevel(), userCreateForm.getBirthDate());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }

        return "redirect:/user/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/id_search")
    public String id_search(UserCreateForm userCreateForm) {
        return "id_search";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/id_search")
    public String id_search(@Valid UserCreateForm userCreateForm, BindingResult bindingResult,
                            Model model, @RequestParam("email") String email, @RequestParam("name") String name) {
        Optional<SiteUser> os = this.userService.getEmailAndName(userCreateForm.getEmail(), userCreateForm.getName());

        SiteUser findUser = os.get();

        //메일 일치 시, 아이디 알려주기
        if (findUser.getEmail().equals(email)) {
            model.addAttribute("findUsername", findUser.getUsername());
        }
        //url 결과 보여 주기
        return userService.getEmailAndName(email, name)
                .map(siteUser ->
                        rq.redirect(
                                "/user/id_search_result?username=%s".formatted(siteUser.getUsername()),
                                "해당 회원의 아이디는 `%s` 입니다.".formatted(siteUser.getUsername())
                        )
                )
                .orElse(rq.historyBack("`%s` (은)는 존재하지 않은 회원입니다."));
    }


    @PreAuthorize("isAnonymous()")
    @GetMapping("/id_search_result")
    public String id_search_result(Model model, @RequestParam(value = "username") String username) {
        model.addAttribute("findUsername", username);
        return "id_search_result";
    }

    // 비번 찾기
    @PreAuthorize("isAnonymous()")
    @GetMapping("/password_search")
    public String password_search(UserCreateForm userCreateForm) {
        return "password_search";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/password_search_modify")
    public String password_search(@Valid UserCreateForm userCreateForm, BindingResult bindingResult,
                                  @RequestParam("username") String username, @RequestParam("email") String email,
                                  @RequestParam("name") String name, Model model) {
        findUser = this.userService.getUserUsernameAndMailAndName(userCreateForm.getUsername(), userCreateForm.getEmail(),
                userCreateForm.getName());
        if(findUser.getEmail().equals(email)&&findUser.getUsername().equals(username)) {
            model.addAttribute("userCreateForm", new UserCreateForm());
        }else {
            throw new RuntimeException("일치하는 값이 존재하지 않습니다.");
        }
        return "password_search_modify";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/password_search_result")
    public String modifyPassword(@ModelAttribute("userCreateForm") UserCreateForm userCreateForm) {
        this.userService.modifyPassword(userCreateForm, findUser);
        return "login";
    }
}