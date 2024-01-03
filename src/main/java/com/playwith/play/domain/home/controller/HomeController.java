package com.playwith.play.domain.home.controller;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matching.entity.MatchingType;
import com.playwith.play.domain.matching.service.MatchingService;
import com.playwith.play.domain.matchingdate.entity.MatchingDate;
import com.playwith.play.domain.matchingdate.service.MatchingDateService;
import com.playwith.play.domain.qna.entity.Qna;
import com.playwith.play.domain.qna.service.QnaService;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
        List<MatchingDate> matchingDates = this.matchingDateService.getList();
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("filteredMatchings1", filteredMatchings1);
        model.addAttribute("matchingDates", matchingDates);
        return "index";
    }

//    @RequestMapping(value = "/filterByArea", method = {RequestMethod.GET, RequestMethod.POST})
    @GetMapping("/filterByArea")
    @ResponseBody
    public List<Matching> filterByArea(@RequestParam("area") String area, Model model) {
        List<Matching> allMatchings = this.matchingService.getList();
        // area에 해당하는 매칭 데이터를 필터링하여 반환
        List<Matching> filteredMatchings1 = allMatchings.stream()
                .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_1 && matching.getArea().equals(area))
                .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                        .thenComparing(Matching::getGameTime))
                .collect(Collectors.toList());
        model.addAttribute("filteredMatchings1", filteredMatchings1);
        return filteredMatchings1;
    }

}
