package com.playwith.play.global.data;

import com.playwith.play.domain.qna.service.QnaService;
import com.playwith.play.domain.user.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!prod")
public class NotProd {
//    @Value("${custom.security.oauth2.client.registration.kakao.devUser.oauthId}")
    private String kakaoDevUserOAuthId;
//    @Value("${custom.security.oauth2.client.registration.kakao.devUser.nickname}")
    private String kakaoDevUserNickname;
//    @Value("${custom.security.oauth2.client.registration.kakao.devUser.profileImgUrl}")
    private String kakaoDevUserProfileImgUrl;

    @Bean
    public ApplicationRunner init(UserService userService) {
        return args -> {
            userService.join("대전","상","홍길순","test","admin@admin.com","1234","길순짱","");

            userService.whenSocialLogin(
                    "KAKAO",
                    "KAKAO__%s".formatted(kakaoDevUserOAuthId),
                    kakaoDevUserNickname,
                    kakaoDevUserProfileImgUrl
            );
        };
    }

//    @Bean
//    public ApplicationRunner init(QnaService qnaService) {
//        return args -> {
//            qnaService.create("질문입니당", "내용입니다.<br>내용입니다.");
//        };
//    }
}