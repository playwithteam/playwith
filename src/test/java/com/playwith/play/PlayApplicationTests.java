package com.playwith.play;

import com.playwith.play.domain.stadium.service.StadiumService;
import com.playwith.play.domain.team.service.TeamService;
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
    @Autowired
    TeamService teamService;

    @Test
    @DisplayName("테스트 아이디")
    void test01() {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            userService.join(null, "test", "신짱아", "qwer1234!", "test@test.com", "대전", "상", null, null);
        });
    }

//    @Test
//    @DisplayName("테스트 구장")
//    void test02(StadiumService stadiumService) {
//        stadiumService.create("서울", "로꼬풋살장", "KR 서울특별시 송파구 잠실동 40-1 롯데마트 제타플렉스점 R층", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d202412.1888398174!2d126.8427373825985!3d37.56235622779598!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357ca5c150f26537%3A0x6946a93f4d550d0!2z66Gc6rys7ZKL7IK07Iqk7YOA65SU7JuAIOyeoOyLpOygnO2DgO2UjOugieyKpOygkA!5e0!3m2!1sko!2skr!4v1703209534380!5m2!1sko!2skr");
//    }
}
