package com.playwith.play.domain.user.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateForm {
    private String profileImgUrl;
    @Size(min = 3, max = 25)
    @NotBlank(message = "사용자ID는 필수항목입니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수항목입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password1;
    @NotBlank(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;
    @NotBlank(message = "이메일은 필수항목입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank(message = "이메일 확인은 필수항목입니다.")
    private String verificationCode;
    @NotBlank(message = "이름은 필수항목입니다.")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "생년월일은 필수항목입니다.")
    private LocalDate birthDate;
    @NotBlank(message = "지역은 필수항목입니다.")
    private String area;
    @NotBlank(message = "레벨은 필수항목입니다.")
    private String level;
}