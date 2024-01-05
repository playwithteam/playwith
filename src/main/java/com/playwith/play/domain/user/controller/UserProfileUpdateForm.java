package com.playwith.play.domain.user.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class UserProfileUpdateForm {
    private MultipartFile profileImage;
    @NotBlank(message = "필수 입력 항목 입니다.")
    private String name;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password1;
    private String password2;
    private String email;
    @NotBlank(message = "필수 입력 항목 입니다.")
    private String area;
    private String level;
    private LocalDate birthDate;
    private Integer rating;
}
