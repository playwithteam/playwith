package com.playwith.play.global.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class Dev {
    @Autowired
    PasswordEncoder passwordEncoder;

//    @Bean
//    public ApplicationRunner init(UserService userService) {
//        return args -> {
//            userService.join("", "test", "홍길순","qwer1234!","test@test.com","","상",null);
//        };
//    }
}

