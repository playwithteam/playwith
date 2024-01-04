package com.playwith.play.domain.matching.controller;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matching.entity.MatchingType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

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
        this.matchingService.create(matchingForm.getMatchingType(), matchingForm.getGameDate(), matchingDate, matchingForm.getGameTime(), matchingForm.getLevel(), matchingForm.getArea(), matchingForm.getStadium(), siteUser.getName());
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(MatchingForm matchingForm ,@PathVariable("id") Long id, Principal principal) {
        Matching matching = this.matchingService.getMatching(id);
        SiteUser siteUser = this.userService.getUserByName(principal.getName());
        matchingForm.setMatchingType(matching.getMatchingType());
        matchingForm.setGameDate(matching.getGameDate());
        matchingForm.setGameTime(matching.getGameTime());
        matchingForm.setArea(matching.getArea());
        matchingForm.setStadium(matching.getStadium());
        matchingForm.setLevel(matching.getLevel());
        return "matching_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Matching matching = this.matchingService.getMatching(id);
        this.matchingService.delete(matching);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/mercenary/{id}")
    public String mercenary(@PathVariable("id") Long id, Principal principal) {
        Matching matching = this.matchingService.getMatching(id);
        SiteUser siteUser = this.userService.getUserByName(principal.getName());
        this.matchingService.mercenary(matching, siteUser);
        return String.format("redirect:/matching/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mercenary/delete/{id}")
    public String mercenaryDelete(@PathVariable("id") Long id, Principal principal) {
        Matching matching = this.matchingService.getMatching(id);
        SiteUser siteUser = this.userService.getUserByName(principal.getName());
        this.matchingService.mercenaryDelete(matching, siteUser);
        return String.format("redirect:/matching/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/history")
    public String history(Model model, Principal principal) {
        String loggedInUsername = principal.getName();
        SiteUser siteUser = this.userService.getUserByName(principal.getName());
        List<Matching> allMatchings = this.matchingService.getList();
        List<Matching> historyMatchings1 = new ArrayList<>();
        List<Matching> historyMatchings2 = new ArrayList<>();

        if (siteUser.getRating() == 1) {
            historyMatchings1 = allMatchings.stream()
                    .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_1 &&
                            matching.getUserList().stream()
                                    .anyMatch(user -> user.getUsername().equals(loggedInUsername)))
                    .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                            .thenComparing(Matching::getGameTime))
                    .collect(Collectors.toList());
            historyMatchings2 = allMatchings.stream()
                    .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_2 &&
                            matching.getUserList().stream()
                                    .anyMatch(user -> user.getUsername().equals(loggedInUsername)))
                    .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                            .thenComparing(Matching::getGameTime))
                    .collect(Collectors.toList());
        }
        else if (siteUser.getRating() == 2) {
            historyMatchings1 = allMatchings.stream()
                    .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_1 &&
                            matching.getManagerName().equals(siteUser.getName()))
                    .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                            .thenComparing(Matching::getGameTime))
                    .collect(Collectors.toList());
            historyMatchings2 = allMatchings.stream()
                    .filter(matching -> matching.getMatchingType() == MatchingType.TYPE_2 &&
                            matching.getManagerName().equals(siteUser.getName()))
                    .sorted(Comparator.comparing((Matching matching) -> matching.getMatchingDate().getGameDate())
                            .thenComparing(Matching::getGameTime))
                    .collect(Collectors.toList());
        }

        model.addAttribute("historyMatchings1", historyMatchings1);
        model.addAttribute("historyMatchings2", historyMatchings2);
        return "matching_history";
    }
}
