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

//    @Bean
//    public ApplicationRunner init(QnaService qnaService) {
//        return args -> {
//            qnaService.create("자주 묻는 질문입니다.", "자주 묻는 내용입니다. 자주 묻는 내용입니다.");
//        };
//    }

    //구장 자동 생성
//    @Bean
//    public ApplicationRunner init(StadiumService stadiumService) {
//        return args -> {
//            stadiumService.create("서울", "로꼬풋살장", "KR 서울특별시 송파구 잠실동 40-1 롯데마트 제타플렉스점 R층", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d202412.1888398174!2d126.8427373825985!3d37.56235622779598!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357ca5c150f26537%3A0x6946a93f4d550d0!2z66Gc6rys7ZKL7IK07Iqk7YOA65SU7JuAIOyeoOyLpOygnO2DgO2UjOugieyKpOygkA!5e0!3m2!1sko!2skr!4v1703209534380!5m2!1sko!2skr");
//            stadiumService.create("서울", "루다풋살장", "서울특별시 도봉구 방학동 271-2", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d202412.1888398174!2d126.8427373825985!3d37.56235622779598!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357cbfc2be51bdb7%3A0xf7fd2c558d26beff!2z66Oo64ukIO2Si-yCtOyepQ!5e0!3m2!1sko!2skr!4v1703209664306!5m2!1sko!2skr");
//            stadiumService.create("대전", "금강풋살장", "대전광역시 대덕구 석봉동 대덕대로 1575", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3209.2913357683046!2d127.42010327533369!3d36.45051508733418!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x356536de54e117a7%3A0x762abfe306877f68!2z6riI6rCV7ZKL7IK07J6l!5e0!3m2!1sko!2skr!4v1703209298748!5m2!1sko!2skr");
//            stadiumService.create("대전", "가장풋살장", "가장동 21-4번지 서구 대전광역시 KR", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3214.2551653071855!2d127.3884220753281!3d36.33036519404405!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x35654952e0a0f503%3A0x3140319938448f3a!2z6rCA7J6l7ZKL7IK06rWs7J6l!5e0!3m2!1sko!2skr!4v1703209328604!5m2!1sko!2skr");
//            stadiumService.create("부산", "첼시풋살장", "부산광역시 강서구 대저1동 742-1","rhttps://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d208618.21647197718!2d128.66986319453125!3d35.21494620000001!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3568c1fe707bd6cd%3A0xabf28c60f8a20a88!2z67aA7IKw7LK87Iuc7ZKL7IK07J6l!5e0!3m2!1sko!2skr!4v1703209792317!5m2!1sko!2sk");
//            stadiumService.create("부산", "HM풋살장", "부산광역시 북구 금곡동 금곡대로 469", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d208618.21647197718!2d128.66986319453125!3d35.21494620000001!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3568959244f252e5%3A0x5c84880006211ac4!2zSE3tkovsgrQg67aA7IKw67aB6rWs!5e0!3m2!1sko!2skr!4v1703209844069!5m2!1sko!2skr");
//        };
//    }
    @Bean
    public ApplicationRunner initQna(QnaService qnaService) {
        return args -> {
            qnaService.create("자주 묻는 질문입니다.", "자주 묻는 내용입니다. 자주 묻는 내용입니다.");
        };
    }
}

