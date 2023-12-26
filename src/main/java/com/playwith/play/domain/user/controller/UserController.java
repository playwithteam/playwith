package com.playwith.play.domain.user.controller;

import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.service.UserService;
import com.playwith.play.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
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

    //회원가입
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

    //아이디 찾기
    @PreAuthorize("isAnonymous()")
    @GetMapping("/id_search")
    public String id_search() {
        return "id_search";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/id_search")
    public ResponseEntity<String> id_search(Model model, @RequestParam("email") String inputEmail,
                                            @RequestParam("name") String inputName) {
        // 사용자의 메일, 이름 얻기
        Optional<SiteUser> foundUser = this.userService.getUserByEmailAndName(inputEmail, inputName);

        if (foundUser.isPresent()) {
            findUser = foundUser.get();
            // 사용자 찾음
            model.addAttribute("findUsername", this.findUser);
            return ResponseEntity.ok("id_search_result");
        } else {
            return ResponseEntity.ok("입력한 정보가 올바르지 않거나 존재하지 않음");
        }
    }

    //아이디 찾기 결과
    @PreAuthorize("isAnonymous()")
    @GetMapping("/id_search_result")
    public String id_search_result(Model model) {
        // findUser가 null이 아닌 경우에만 모델에 추가
        if (this.findUser != null) {
            model.addAttribute("findUsername", this.findUser.getUsername());
        }
        return "id_search_result";
    }

    // 비번 찾기
    @PreAuthorize("isAnonymous()")
    @GetMapping("/password_search")
    public String password_search() {
        return "password_search";
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/password_search")
    public ResponseEntity<String> password_search(Model model, @RequestParam("username") String inputUsername,
                                                  @RequestParam("email") String inputEmail, @RequestParam("name") String inputName) {
        // 사용자의 아이디, 메일, 이름 얻기
      Optional<SiteUser> findUser= this.userService.getUserUsernameAndMailAndName(inputUsername, inputEmail, inputName);

            model.addAttribute("newPasswordForm", findUser);
            return ResponseEntity.ok("password_search_modify");

    }
    @PreAuthorize("permitAll()")
    @GetMapping("/password_search_modify")
    public String password_search_modify(Model model) {
        model.addAttribute("newPasswordForm", this.findUser);
        return "password_search_modify";
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/password_search_result")
    public ResponseEntity<String> modifyPassword(Model model, NewPasswordForm newPasswordForm) {
        this.userService.modifyPassword(newPasswordForm, findUser);
        model.addAttribute("newPasswordForm", findUser);
        return ResponseEntity.ok("login");
    }
}

