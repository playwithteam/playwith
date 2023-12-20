package com.playwith.play;

import com.playwith.play.domain.qna.service.QnaService;
import com.playwith.play.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
class PlayApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private QnaService qnaService;

    @Test
    @DisplayName("테스트 아이디")
    void init() {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            userService.join("user", "홍길동", "1234", "user@user.com", "대전",
                    "3", "길동이" + i, "", "3");
        });
    }

    @Test
    @DisplayName("자주 찾는 질문 테스트 내용")
    void testQna() {
        for (int i = 1; i <= 10; i++) {
            String title = "질문입니다." + i + ".";
            String content = "내용입니다.내용입니다.<br>내용입니다.내용입니다.<br>내용입니다.내용입니다.";
            qnaService.create(title, content);
        }
    }

}
