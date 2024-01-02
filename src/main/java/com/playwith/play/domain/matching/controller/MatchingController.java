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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/matching")
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;
    private final StadiumService stadiumService;
    private final MatchingDateService matchingDateService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(MatchingForm matchingForm) {
        return "matching_form";
    }

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, Principal principal) {
        Matching matching = this.matchingService.getMatching(id);
        String currentUsername = principal.getName();
        List<SiteUser> userList = matching.getUserList();
        boolean isCurrentUserInList = userList.stream()
                .anyMatch(user -> user.getUsername().equals(currentUsername));
        model.addAttribute("matching", matching);
        model.addAttribute("isCurrentUserInList", isCurrentUserInList);
        return "matching_detail";
    }

    @PostMapping("/mercenary/{id}")
    public String mercenary(@PathVariable("id") Long id, Principal principal) {
        Matching matching = this.matchingService.getMatching(id);
        SiteUser siteUser = this.userService.getUserByName(principal.getName());
        this.matchingService.mercenary(matching, siteUser);
        return String.format("redirect:/matching/detail/%s", id);
    }

    @GetMapping("/mercenary/delete/{id}")
    public String mercenaryDelete(@PathVariable("id") Long id, Principal principal) {
        Matching matching = this.matchingService.getMatching(id);
        SiteUser siteUser = this.userService.getUserByName(principal.getName());
        this.matchingService.mercenaryDelete(matching, siteUser);
        return String.format("redirect:/matching/detail/%s", id);
    }
}
