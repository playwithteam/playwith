package com.playwith.play.global.data;

import com.playwith.play.domain.matchingdate.service.MatchingDateService;
import com.playwith.play.domain.qna.service.QnaService;
import com.playwith.play.domain.stadium.service.StadiumService;
import com.playwith.play.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@Profile("dev")
public class Dev {
    @Autowired
    PasswordEncoder passwordEncoder;

}

