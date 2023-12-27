package com.playwith.play.global.data;

import com.playwith.play.domain.qna.service.QnaService;
import com.playwith.play.domain.stadium.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class Dev {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    QnaService qnaService;

    @Autowired
    StadiumService stadiumService;

    @Bean
    public ApplicationRunner initQna(QnaService qnaService) {
        return args -> {
            qnaService.create("자주 묻는 질문입니다.", "자주 묻는 내용입니다. 자주 묻는 내용입니다.");
        };
    }
}

