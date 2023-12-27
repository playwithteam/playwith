package com.playwith.play.domain.user.controller;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateForm {
    private MultipartFile profileImage;
    @Size(min = 3, max = 25, message = "아이디는 3자 ~ 25자를 사용하세요.")
    @NotBlank(message = "필수 입력 항목 입니다.")
    private String username;
    @NotBlank(message = "필수 입력 항목 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password1;
    @NotBlank(message = "필수 입력 항목 입니다.")
    private String password2;
    @NotBlank(message = "필수 입력 항목 입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank(message = "필수 입력 항목 입니다.")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "필수 입력 항목 입니다.")
    private LocalDate birthDate;
    @NotBlank(message = "필수 입력 항목 입니다.")
    private String area;
    @NotBlank(message = "필수 입력 항목 입니다.")
    private String level;
}