package com.playwith.play.domain.matching.controller;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matching.service.MatchingService;
import com.playwith.play.domain.matchingdate.entity.MatchingDate;
import com.playwith.play.domain.matchingdate.service.MatchingDateService;
import com.playwith.play.domain.qna.controller.QnaForm;
import com.playwith.play.domain.stadium.entity.Stadium;
import com.playwith.play.domain.stadium.service.StadiumService;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.service.UserService;
import com.playwith.play.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/matching")
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;
    private final StadiumService stadiumService;
    private final MatchingDateService matchingDateService;
    private final UserService userService;

    @GetMapping("/create")
    public String create(MatchingForm matchingForm) {
        return "matching_form";
    }

    @PostMapping("/create")
    public String create(@Valid MatchingForm matchingForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "matching_form";
        }
        MatchingDate matchingDate = this.matchingDateService.getMatchingDate(matchingForm.getGameDate());
        SiteUser siteUser = this.userService.getUserByName(principal.getName());
        this.matchingService.create(matchingForm.getMatchingType(), matchingDate, matchingForm.getGameTime(), matchingForm.getLevel(), matchingForm.getArea(), matchingForm.getStadium(), siteUser.getName());
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Matching matching = this.matchingService.getMatching(id);
        model.addAttribute("matching", matching);
        return "matching_detail";
    }
}
