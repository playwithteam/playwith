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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
        List<Matching> matchingList = this.matchingService.getList();
        List<MatchingDate> matchingDates = this.matchingDateService.getList();
        long type1Count = matchingList.stream()
                .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_1)
                .count();
        long type2Count = matchingList.stream()
                .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_2)
                .count();
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("matchingList", matchingList);
        model.addAttribute("type1Count", type1Count);
        model.addAttribute("type2Count", type2Count);
        model.addAttribute("matchingDates", matchingDates);

        return "index";
    }

}
