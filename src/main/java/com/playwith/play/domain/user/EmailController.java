package com.playwith.play.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;


    // 임시 비밀번호 발급
//    @PostMapping("/password")
//    public ResponseEntity sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
//        EmailMessage emailMessage = EmailMessage.builder()
//                .to(emailPostDto.getEmail())
//                .subject("")
//                .build();
//
//        emailService.sendMail(emailMessage, "password");
//
//        return ResponseEntity.ok().build();
//    }

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    public ResponseEntity<EmailResponseDto> sendJoinMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[PLAY WITH] 이메일 인증을 위한 인증 코드")
                .build();

        String code = emailService.sendMail(emailMessage, "email");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        return ResponseEntity.ok(emailResponseDto);
    }
}