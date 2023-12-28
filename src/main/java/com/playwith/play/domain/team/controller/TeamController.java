package com.playwith.play.domain.team.controller;

import com.playwith.play.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    //팀정보
    @GetMapping("/teamless")
    public String teamless() {
        return "teamless";
    }

    @GetMapping("/team_create")
    public String teamCreate(Model model, TeamCreateForm teamCreateForm) {
//        model.addAttribute("teamCreateForm", new TeamCreateForm());
        return "team_create";
    }

    @PostMapping("/team_create")
    public String teamCreate(@Valid TeamCreateForm teamCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "team_create";
        }
        MultipartFile profileImage = teamCreateForm.getProfileImage();

        this.teamService.createTeam(profileImage, teamCreateForm.getTeamName(), teamCreateForm.getArea(), teamCreateForm.getLevel());
        return String.format("redirect:/team/teamCreate");
    }

    @GetMapping("/checkTeamName")
    @ResponseBody
    public ResponseEntity<String> checkTeamName(@RequestParam("teamName") String teamName) {

        if (teamName == null || teamName.trim().isEmpty()) {
            return ResponseEntity.ok("팀명을 입력해주세요.");
        }
        if (teamService.isTeamNameUnique(teamName)) {
            return ResponseEntity.ok("사용 가능 합니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이름 입니다.");
        }
    }
}
