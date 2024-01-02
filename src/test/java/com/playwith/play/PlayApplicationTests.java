package com.playwith.play;

import com.playwith.play.domain.stadium.service.StadiumService;
import com.playwith.play.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class PlayApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    StadiumService stadiumService;

    @Test
    @DisplayName("테스트 아이디")
    void test01() {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            userService.join(null,"test","신짱구", "qwer1234!", "test@test.com", "대전", "상", null, 2);
            userService.join(null,"t111","빵빵이", "qwer1234!", "test111@test.com", "", "", null, 2);
        });
    }
}
