package com.playwith.play.domain.matching.controller;

import com.playwith.play.domain.matching.service.MatchingService;
import com.playwith.play.domain.qna.controller.QnaForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/matching")
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;

    @GetMapping("/create")
    public String create(MatchingForm matchingForm) {
        return "matching_form";
    }

    @PostMapping("/create")
    public String create(@Valid MatchingForm matchingForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "matching_form";
        }
        this.matchingService.create(matchingForm.getMatchingType(), matchingForm.getGameDate(), matchingForm.getGameTime(), matchingForm.getLevel(), matchingForm.getArea(), matchingForm.getBetel());
        return "redirect:/";
    }
}
