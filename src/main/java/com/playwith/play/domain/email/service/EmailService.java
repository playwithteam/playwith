package com.playwith.play.domain.email.service;

import com.playwith.play.domain.email.dto.EmailMessage;
import com.playwith.play.domain.email.entity.EmailVerification;
import com.playwith.play.domain.email.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final EmailRepository emailRepository;

    public String sendMail(EmailMessage emailMessage, String type) {
        String authNum = createCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("Success");
            saveVerificationCode(emailMessage.getTo(), authNum);
            return authNum;

        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }
    public boolean verifyCode(String email, String userEnteredCode) {
        Optional<EmailVerification> optionalEmailVerification =
                emailRepository.findByEmailAndVerificationCode(email, userEnteredCode);
        return optionalEmailVerification.isPresent() &&
                optionalEmailVerification.get().getVerificationCode().equals(userEnteredCode);
    }

    // 인증번호 저장
    @Transactional
    public void saveVerificationCode(String email, String verificationCode) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(verificationCode);
        emailRepository.save(emailVerification);
    }
    // 인증번호 생성 메서드
    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    // thymeleaf를 통한 html 적용
    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }
}
