package com.playwith.play.domain.user.controller;

import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.service.UserService;
import com.playwith.play.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Rq rq;
    private String findUserName; //아이디 값 담을 변수 선언

    //로그인
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String login() {
        return "login";

    }

    //회원가입
    @PreAuthorize("isAnonymous()")
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        MultipartFile profileImage = userCreateForm.getProfileImage();
        userService.join(profileImage, userCreateForm.getUsername(), userCreateForm.getName(),
                userCreateForm.getPassword1(), userCreateForm.getEmail(),
                userCreateForm.getArea(), userCreateForm.getLevel(), userCreateForm.getBirthDate(), null, 1);

        redirectAttributes.addFlashAttribute("msg", "회원가입이 완료되었습니다. 로그인페이지로 이동합니다.");
        return "redirect:/user/login";
    }

    @GetMapping("/checkUsername")
    @ResponseBody
    public ResponseEntity<String> checkUsername(@RequestParam("username") String username) {
        if (userService.isUsernameUnique(username)) {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 아이디입니다.");
        }
    }

    //아이디 찾기
    @PreAuthorize("isAnonymous()")
    @GetMapping("/id_search")
    public String id_search() {
        return "id_search";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/id_search")
    public ResponseEntity<String> id_search(@RequestParam("email") String inputEmail,
                                            @RequestParam("name") String inputName) {
        // 사용자의 메일, 이름 얻기
        Optional<SiteUser> foundUser = this.userService.getUserByEmailAndName(inputEmail, inputName);

        if (foundUser.isPresent()) {
            findUserName = foundUser.get().getUsername();

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
        if (this.findUserName != null) {
            SiteUser findUser = this.userService.findByUsername(findUserName);
            model.addAttribute("findUsername", findUser);
            findUserName = null;
        }
        return "id_search_result";
    }

    // 비번 찾기
    @PreAuthorize("isAnonymous()")
    @GetMapping("/password_search")
    public String password_search() {
        return "password_search";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/password_search_confirm")
    public ResponseEntity<String> password_search(@RequestParam("username") String inputUsername,
                                                  @RequestParam("email") String inputEmail, @RequestParam("name") String inputName) {
        // 사용자의 아이디, 메일, 이름 얻기
        Optional<SiteUser> findUser = this.userService.getUserUsernameAndMailAndName(inputUsername, inputEmail, inputName);
        findUserName = findUser.get().getUsername();

        return ResponseEntity.ok("password_search_modify");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/password_search_modify")
    public String password_search_modify(Model model) {
        model.addAttribute("newPasswordForm", new NewPasswordForm());
        return "password_search_modify";
    }

    //비번 찾은 후 로그인 페이지 이동
    @PreAuthorize("isAnonymous()")
    @PostMapping("/password_search_result")
    public ResponseEntity<String> modifyPassword(@ModelAttribute("newPasswordForm") NewPasswordForm newPasswordForm) {
        SiteUser findUser = this.userService.findByUsername(findUserName);
        this.userService.modifyPassword(newPasswordForm, findUser);
        findUserName = null;
        return ResponseEntity.ok("login");
    }

    //내정보
    @GetMapping("/mypage")
    public String mypage(Model model) {
        // 현재 로그인한 사용자의 정보를 가져오기
        SiteUser user = rq.getMember();

        // 가져온 정보를 모델에 추가
        model.addAttribute("user", user);
        model.addAttribute("userCreateForm", new UserCreateForm());
        model.addAttribute("userProfileUpdateForm", new UserProfileUpdateForm());

        return "mypage";
    }

    @PostMapping("/mypage")
    public String mypage(@Valid UserProfileUpdateForm userProfileUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "mypage";
        }
        MultipartFile profileImage = userProfileUpdateForm.getProfileImage();
        // 현재 로그인한 사용자 정보 가져오기
        SiteUser loggedInUser = rq.getMember();
        Team team = loggedInUser.getTeam();
        // 사용자 정보 수정
        userService.modifyUser(profileImage, loggedInUser.getUsername(), userProfileUpdateForm.getName(),
                userProfileUpdateForm.getPassword1(), userProfileUpdateForm.getEmail(),
                userProfileUpdateForm.getArea(), userProfileUpdateForm.getLevel(), userProfileUpdateForm.getBirthDate(), team, userProfileUpdateForm.getRating());

        model.addAttribute("userProfileUpdateForm", userProfileUpdateForm);

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/checkPassword")
    public ResponseEntity<String> checkCurrentPassword(@RequestParam("enteredPassword") String enteredPassword, Principal principal) {
        boolean isPasswordMatch = userService.isPasswordMatching(enteredPassword, principal);

        if (isPasswordMatch) {
            return ResponseEntity.ok("현재 비밀번호가 일치합니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("현재 비밀번호가 일치하지 않습니다.");
        }
    }
}

