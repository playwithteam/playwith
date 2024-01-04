package com.playwith.play;

import com.playwith.play.domain.matching.entity.MatchingType;
import com.playwith.play.domain.matching.service.MatchingService;
import com.playwith.play.domain.matchingdate.entity.MatchingDate;
import com.playwith.play.domain.matchingdate.service.MatchingDateService;
import com.playwith.play.domain.qna.service.QnaService;
import com.playwith.play.domain.stadium.entity.Stadium;
import com.playwith.play.domain.stadium.service.StadiumService;
import com.playwith.play.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@SpringBootTest
class PlayApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private MatchingService matchingService;
    @Autowired
    private StadiumService stadiumService;
    @Autowired
    private QnaService qnaService;
    @Autowired
    private MatchingDateService matchingDateService;

    @Test
    @DisplayName("기본 데이터 생성")
    void test01() {
        // 매니저 아아디 생성

        userService.join(null,"test1","빵빵이", "1234", "test1@test.com", "", "", null, null, 2);
        userService.join(null,"test2","신짱아", "1234", "test2@test.com", "", "", null, null, 2);
        userService.join(null,"test3","뚱이", "1234", "test3@test.com", "", "", null, null, 2);
        userService.join(null,"user1","손흥민", "1234", "user1@test.com", "춘천", "상", LocalDate.of(1992, 7, 8), null, 1);
        userService.join(null,"user2","김민재", "1234", "user2@test.com", "통영", "상", LocalDate.of(1996, 11, 15), null, 1);
        userService.join(null,"user3","이강인", "1234", "user3@test.com", "인천", "상", LocalDate.of(2001, 2, 19), null, 1);
        userService.join(null,"user4","차두리", "1234", "user4@test.com", "서울", "상", LocalDate.of(1980, 7, 25), null, 1);
        userService.join(null,"user5","김경호", "1234", "user5@test.com", "대전", "하", LocalDate.of(1996, 8, 21), null, 1);
        userService.join(null,"user6","송강호", "1234", "user6@test.com", "부산", "중", LocalDate.of(1973, 12, 1), null, 1);
        userService.join(null,"user7","정우성", "1234", "user7@test.com", "서울", "상", LocalDate.of(1975, 7, 19), null, 1);
        userService.join(null,"user8","강동원", "1234", "user8@test.com", "포항", "중", LocalDate.of(1977, 12, 3), null, 1);
        userService.join(null,"user9","설영우", "1234", "user9@test.com", "수원", "상", LocalDate.of(1998, 7, 2), null, 1);
        userService.join(null,"user10","이기제", "1234", "user10@test.com", "수원", "상", LocalDate.of(1991, 9, 4), null, 1);
        userService.join(null,"user11","김영권", "1234", "user11@test.com", "인천", "중", LocalDate.of(1988, 4, 15), null, 1);
        userService.join(null,"user12","송민규", "1234", "user12@test.com", "서울", "상", LocalDate.of(1998, 3, 21), null, 1);
        userService.join(null,"user13","오현규", "1234", "user13@test.com", "수원", "하", LocalDate.of(2000, 1, 3), null, 1);
        userService.join(null,"user14","이청용", "1234", "user14@test.com", "대구", "상", LocalDate.of(1988, 4, 24), null, 1);
        userService.join(null,"user15","이운재", "1234", "user15@test.com", "창원", "상", LocalDate.of(1975, 9, 4), null, 1);
        userService.join(null,"user16","김신욱", "1234", "user16@test.com", "서울", "중", LocalDate.of(1988, 7, 2), null, 1);

        // 기본 구장 생성
        stadiumService.create("서울", "로꼬풋살장", "KR 서울특별시 송파구 잠실동 40-1 롯데마트 제타플렉스점 R층", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d202412.1888398174!2d126.8427373825985!3d37.56235622779598!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357ca5c150f26537%3A0x6946a93f4d550d0!2z66Gc6rys7ZKL7IK07Iqk7YOA65SU7JuAIOyeoOyLpOygnO2DgO2UjOugieyKpOygkA!5e0!3m2!1sko!2skr!4v1703209534380!5m2!1sko!2skr");
        stadiumService.create("서울", "루다풋살장", "서울특별시 도봉구 방학동 271-2", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d202412.1888398174!2d126.8427373825985!3d37.56235622779598!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357cbfc2be51bdb7%3A0xf7fd2c558d26beff!2z66Oo64ukIO2Si-yCtOyepQ!5e0!3m2!1sko!2skr!4v1703209664306!5m2!1sko!2skr");
        stadiumService.create("대전", "금강풋살장", "대전광역시 대덕구 석봉동 대덕대로 1575", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3209.2913357683046!2d127.42010327533369!3d36.45051508733418!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x356536de54e117a7%3A0x762abfe306877f68!2z6riI6rCV7ZKL7IK07J6l!5e0!3m2!1sko!2skr!4v1703209298748!5m2!1sko!2skr");
        stadiumService.create("대전", "가장풋살장", "가장동 21-4번지 서구 대전광역시 KR", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3214.2551653071855!2d127.3884220753281!3d36.33036519404405!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x35654952e0a0f503%3A0x3140319938448f3a!2z6rCA7J6l7ZKL7IK06rWs7J6l!5e0!3m2!1sko!2skr!4v1703209328604!5m2!1sko!2skr");
        stadiumService.create("부산", "첼시풋살장", "부산광역시 강서구 대저1동 742-1", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d208618.21647197718!2d128.66986319453125!3d35.21494620000001!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3568c1fe707bd6cd%3A0xabf28c60f8a20a88!2z67aA7IKw7LK87Iuc7ZKL7IK07J6l!5e0!3m2!1sko!2skr!4v1703209792317!5m2!1sko!2sk");
        stadiumService.create("부산", "HM풋살장", "부산광역시 북구 금곡동 금곡대로 469", "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d208618.21647197718!2d128.66986319453125!3d35.21494620000001!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3568959244f252e5%3A0x5c84880006211ac4!2zSE3tkovsgrQg67aA7IKw67aB6rWs!5e0!3m2!1sko!2skr!4v1703209844069!5m2!1sko!2skr");

        // 오늘 날짜+1 기준으로 2주 매칭 날짜 생성
        LocalDate currentDate = LocalDate.now().plusDays(1);
        LocalDate endDate = currentDate.plusWeeks(2);

        while (currentDate.isBefore(endDate)) {
            matchingDateService.create(currentDate);
            currentDate = currentDate.plusDays(1);
        }


        // 자주 묻는 질문 생성
        qnaService.create("소셜 매치 취소/변경/환불 규정을 알고 싶어요.", "매치 2일 전: 무료 취소<br>매치 1일 전: 80% 환급<br>당일 ~ 매치 시작 90분 전까지: 20% 환급<br>매치 시작 90분 이내: 환불 불가");
        qnaService.create("소셜 매치 우천/폭설 정책을 알고 싶어요.", "기상청 날씨누리 예보에 따라 진행 및 환불 가능 여부를 강수 알림톡을 통해 공지해드립니다.<br>‘강수 안내 알림톡’을 받고, 매치 시작 90분 전까지 취소하면 전액 환불됩니다.");
        qnaService.create("매치 진행을 위한 최소 인원이 궁금해요", "매치 성격 및 구장 크기에 따라 진행을 위한 최소 인원은 달라질 수 있습니다. ");
        qnaService.create("진행 여부를 언제 알 수 있나요?", "최종 진행 여부는 매치 시작 1시간 30분 전에 결정됩니다.<br>매치 시작 1시간 30분 전까지 최소 인원이 모일 경우 매치가 확정되며, 확정/취소 알림톡을 통해 참가자분들께 진행 여부를 안내 드리고 있어요.");
        qnaService.create("친구와 함께 매치에 참여하고 싶어요.", "플랩풋볼은 정확한 레벨 측정, 원활한 경기 및 매너 카드 시스템 운영을 위해 1계정 당 1명의 참가자만 신청 및 참여가 가능합니다.<br>친구와 함께 매치를 신청하고 싶다면 각각 본인의 계정으로 동일한 매치에 신청해주셔야 합니다.");
        qnaService.create("축구화를 신어도 되나요?", "플랩풋볼에서는 부상 방지와 구장 관리를 위해 스터드가 있는 축구화 착용을 기본적으로 금지하고 있어요.<br>축구화 착용 시 옐로 카드 혹은 레드 카드가 발급 될 수 있으며, 현장에서 귀가 조치됩니다.");
        qnaService.create("진행 방식이 궁금해요", "확정 알림톡 확인 후 매치 시간 및 구장 위치를 확인해 풋살장으로 도착해주세요.<br>현장 매니저가 시간에 맞춰 인원 체크 후 조끼 분배 및 진행을 도와줄 거에요. ");
        qnaService.create("매니저에게 내 지각 소식을 알리고 싶어요.", "미리 매니저에게 지각 사실을 전달하고 싶다면, ⓞ Match Ready 확정톡의 지각 알리기 기능을 적극 이용해주세요.<br>위 알림톡은 매치가 확정되었을 때 카카오톡 알림톡을 통해 발송되고 있어요.");
        qnaService.create("고정 골키퍼를 할 수 있나요?", "매치 진행 동안 인원 및 현장 상황에 따라 필드, 골키퍼를 번갈아 가면서 할 수 있도록 매니저가 로테이션을 조절하고 있어요.<br>다만 골키퍼로만 매치에 참여하고 싶다면, 매치 시작 전 현장에서 매니저, 같은 팀 참가자와 소통해주세요.<br>같은 팀 참가자의 동의를 얻는다면 골키퍼로만 참여가 가능합니다.");
        qnaService.create("참여 가능한 나이 제한이 있나요?", "안전 상의 이유로 고등학생(만 16세)이상만 참여가 가능합니다.<br>보호자 동반 유무와 관계없이 현장에서 만 16세 미만의 참가자로 확인되는 경우 참가가 제한돼요.");
        qnaService.create("매니저가 현장에 늦게 도착했어요", "매니저의 지각으로 인해 매치가 정상 진행되지 못했다면,<br>지각자, 무단 불참자를 제외하고 지연된 시간만큼 다음 날 오전 중으로 부분 환불 처리를 도와드리고 있어요.");

        MatchingType matchingType1 = MatchingType.TYPE_1;
        MatchingType matchingType2 = MatchingType.TYPE_2;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");

        LocalDate localDate1 = LocalDate.now().plusDays(1);
        LocalDate localDate2 = LocalDate.now().plusDays(2);

        MatchingDate matchingDate1 = this.matchingDateService.getMatchingDate(localDate1);
        MatchingDate matchingDate2 = this.matchingDateService.getMatchingDate(localDate2);

        LocalTime localTime1 = LocalTime.of(18, 0);
        LocalTime localTime2 = LocalTime.of(19, 0);
        LocalTime localTime3 = LocalTime.of(20, 0);
        LocalTime localTime4 = LocalTime.of(21, 0);
        LocalTime localTime5 = LocalTime.of(22, 0);

        Stadium testStadium1 = this.stadiumService.getStadiumsByName("로꼬풋살장");
        Stadium testStadium2 = this.stadiumService.getStadiumsByName("금강풋살장");
        Stadium testStadium3 = this.stadiumService.getStadiumsByName("첼시풋살장");

        //매칭 데이터 생성
        //당일
        matchingService.create(matchingType1, localDate1, matchingDate1, localTime1, "상", "대전", testStadium2, "빵빵이");
        matchingService.create(matchingType1, localDate1, matchingDate1, localTime1, "중", "서울", testStadium1, "빵빵이");
        matchingService.create(matchingType1, localDate1, matchingDate1, localTime2, "상", "부산", testStadium3, "빵빵이");
        matchingService.create(matchingType1, localDate1, matchingDate1, localTime3, "상", "서울", testStadium1, "신짱아");
        matchingService.create(matchingType1, localDate1, matchingDate1, localTime4, "하", "대전", testStadium2, "뚱이");
        matchingService.create(matchingType1, localDate1, matchingDate1, localTime5, "상", "서울", testStadium1, "빵빵이");

        //당일+1
        matchingService.create(matchingType1, localDate2, matchingDate2, localTime1, "중", "서울", testStadium1, "뚱이");
        matchingService.create(matchingType1, localDate2, matchingDate2, localTime2, "중", "부산", testStadium3, "빵빵이");
        matchingService.create(matchingType1, localDate2, matchingDate2, localTime2, "상", "대전", testStadium2, "뚱이");
        matchingService.create(matchingType1, localDate2, matchingDate2, localTime3, "하", "대전", testStadium2, "신짱아");

    }

}
