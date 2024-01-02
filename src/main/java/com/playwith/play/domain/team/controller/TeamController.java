package com.playwith.play.domain.team.controller;

import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.team.service.TeamService;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;


    //팀정보
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team_list")
    public String team_list(Model model, Principal principal) {
        List<Team> teamList = this.teamService.getTeamList();
        model.addAttribute("teamList", teamList);

        SiteUser siteUser = userService.findByUsername(principal.getName());

        if (siteUser.getTeam() != null) {
            return String.format("redirect:/team/team_detail/%s", siteUser.getTeam().getId());
        } else {
            return "team_list";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team_create")
    public String team_create(TeamCreateForm teamCreateForm) {
        return "team_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/team_detail/{id}")
    public ResponseEntity<?> team_create(@Valid TeamCreateForm teamCreateForm, BindingResult bindingResult,Principal principal) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("BindingResult error");
        }
        SiteUser siteUser = this.userService.findByUsername(principal.getName());
        Team createdTeam = this.teamService.createTeam(teamCreateForm.getProfileImage(), teamCreateForm.getTeamName(), teamCreateForm.getArea(), teamCreateForm.getLevel(), siteUser);

        if (createdTeam != null) {
            return ResponseEntity.ok().body(String.format("/team/team_detail/%s", createdTeam.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Team creation failed");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team_detail/{id}")
    public String team_detail(Model model, @PathVariable("id") Long id) {
        Team team = this.teamService.getTeam(id);
        model.addAttribute("team", team);
        model.addAttribute("teamCreateForm", new TeamCreateForm());
        return "team_detail";
    }

    @GetMapping("/check_teamname")
    @ResponseBody
    public ResponseEntity<String> checkTeamName(@RequestParam("teamName") String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            // teamName이 비어 있을 때의 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("팀명을 다시 입력하세요.");
        }
        // 팀명이 주어진 경우에 대한 추가적인 로직
        if (teamService.isTeamNameUnique(teamName)) {
            return ResponseEntity.ok("사용 가능한 팀명입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 팀명입니다.");
        }
    }
}
