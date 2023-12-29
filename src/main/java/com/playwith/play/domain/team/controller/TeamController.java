package com.playwith.play.domain.team.controller;

import com.playwith.play.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    //팀정보

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/teamless")
    public String teamless() {
        return "teamless";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team_create")
    public String teamCreate(Model model) {
        model.addAttribute("teamCreateForm", new TeamCreateForm());
        return "team_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/team_create")
    public String teamCreate(@Valid TeamCreateForm teamCreateForm,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "team_create";
        }
        MultipartFile profileImage = teamCreateForm.getProfileImage();
        this.teamService.createTeam(profileImage, teamCreateForm.getTeamName(), teamCreateForm.getArea(), teamCreateForm.getLevel());

            redirectAttributes.addFlashAttribute("msg", "팀 등록이 완료되었습니다.");
            return String.format("redirect:/team/team_create");
    }

    @GetMapping("/checkTeamname")
    @ResponseBody
    public ResponseEntity<String> checkTeamName(@RequestParam("teamName") String teamName) {

        if (teamName == null || teamName.trim().isEmpty()) {
            // teamName이 비어 있을 때의 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("팀명을요 다시 입력하세요.");
        }
        if (teamService.isTeamNameUnique(teamName)) {
            return ResponseEntity.ok("사용 가능한 팀명입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 팀명입니다.");
        }
    }
}
