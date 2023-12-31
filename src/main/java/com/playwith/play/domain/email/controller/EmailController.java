package com.playwith.play.domain.email.controller;

import com.playwith.play.domain.email.dto.EmailMessage;
import com.playwith.play.domain.email.dto.EmailPostDto;
import com.playwith.play.domain.email.dto.EmailResponseDto;
import com.playwith.play.domain.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

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

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody EmailPostDto emailPostDto) {
        String userEmail = emailPostDto.getEmail();
        String userEnteredCode = emailPostDto.getCode();

        if (emailService.verifyCode(userEmail, userEnteredCode)) {
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.badRequest().body("인증 실패");
        }
    }
}