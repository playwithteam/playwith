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


}