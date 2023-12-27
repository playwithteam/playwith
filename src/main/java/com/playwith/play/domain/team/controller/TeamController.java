package com.playwith.play.domain.team.controller;

import com.playwith.play.domain.team.service.TeamService;
import com.playwith.play.domain.user.controller.TeamCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class TeamController {

    private final TeamService teamService;

    //팀정보
    @GetMapping( "/teamless")
    public String teamless() {
        return "teamless";
    }
    @GetMapping("/team_create")
    public String teamCreate(Model model, TeamCreateForm teamCreateForm) {
        model.addAttribute("teamCreateForm", new TeamCreateForm());
        return "team_create";
    }
    @PostMapping("/teamCreate")
    public String teamCreate(@Valid TeamCreateForm teamCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "team_create";
        }
        this.teamService.createTeam(teamCreateForm.getTeamName(), teamCreateForm.getArea(), teamCreateForm.getLevel());
        return String.format("redirect:/user/teamCreate");
    }
}
