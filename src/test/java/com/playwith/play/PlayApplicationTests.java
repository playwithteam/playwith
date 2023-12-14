package com.playwith.play;

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

    @Test
    @DisplayName("테스트 아이디")
    void init() {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            userService.join("user", "홍길동", "1234", "user@user.com", "대전",
                    "3", "길동이" + i, "", "3");
        });
    }

}
