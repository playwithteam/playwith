package com.playwith.play.domain.user.controller;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateForm {
    private String profileImgUrl;
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
    @NotEmpty(message = "이메일 확인은 필수항목입니다.")
    private String verificationCode;

    @NotEmpty(message = "이름은 필수항목입니다.")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "생년월일은 필수항목입니다.")
    private LocalDate birthDate;
    @NotEmpty(message = "지역은 필수항목입니다.")
    private String area;
    @NotEmpty(message = "레벨은 필수항목입니다.")
    private String level;
    @NotEmpty(message = "필수 체크 항목입니다.")
    private String privacyAgreement;
}