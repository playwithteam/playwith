package com.playwith.play.domain.home.controller;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matching.entity.MatchingType;
import com.playwith.play.domain.matching.service.MatchingService;
import com.playwith.play.domain.matchingdate.entity.MatchingDate;
import com.playwith.play.domain.matchingdate.service.MatchingDateService;
import com.playwith.play.domain.qna.entity.Qna;
import com.playwith.play.domain.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@EnableJpaAuditing
public class HomeController {
    private final QnaService qnaService;
    private final MatchingService matchingService;
    private final MatchingDateService matchingDateService;

    @GetMapping("/")
    public String main(Model model) {
        List<Qna> qnaList = this.qnaService.getList();
        List<Matching> allMatchings = this.matchingService.getList();
        List<Matching> filteredMatchings1 = allMatchings.stream()
                .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_1)
                .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                        .thenComparing(Matching::getGameTime))
                .collect(Collectors.toList());
        List<Matching> filteredMatchings2 = allMatchings.stream()
                .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_2)
                .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                        .thenComparing(Matching::getGameTime))
                .collect(Collectors.toList());
        List<MatchingDate> matchingDates = this.matchingDateService.getList();
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("filteredMatchings1", filteredMatchings1);
        model.addAttribute("filteredMatchings2", filteredMatchings2);
        model.addAttribute("matchingDates", matchingDates);

        return "index";
    }

    @GetMapping("/filterMatching")
    public String filterByArea(@RequestParam("area") String area, @RequestParam("gameTime") String gameTime, @RequestParam("level") String level, Model model) {
        List<Matching> allMatchings = this.matchingService.getList();
        List<MatchingDate> matchingDates = this.matchingDateService.getList();
        List<Matching> filteredMatchings1;

        // 지역에 대한 필터링
        if ("all".equals(area)) {
            filteredMatchings1 = allMatchings.stream()
                    .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_1)
                    .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                            .thenComparing(Matching::getGameTime))
                    .collect(Collectors.toList());
        } else {
            filteredMatchings1 = allMatchings.stream()
                    .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_1 && matching.getArea().equals(area))
                    .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                            .thenComparing(Matching::getGameTime))
                    .collect(Collectors.toList());
        }

        // 시간에 대한 필터링
        if (!"all".equals(gameTime)) {
            int selectedGameTime = Integer.parseInt(gameTime);
            LocalTime localTime = LocalTime.of(selectedGameTime, 0); // 분은 0으로 설정
            filteredMatchings1 = filteredMatchings1.stream()
                    .filter(matching -> matching.getGameTime().equals(localTime))
                    .collect(Collectors.toList());
        }

        // 난이도에 대한 필터링
        if (!"all".equals(level)) {
            filteredMatchings1 = filteredMatchings1.stream()
                    .filter(matching -> matching.getLevel().equals(level))
                    .collect(Collectors.toList());
        }

        model.addAttribute("filteredMatchings1", filteredMatchings1);
        model.addAttribute("matchingDates", matchingDates);
        return "index::#matchingsArea";
    }

}
