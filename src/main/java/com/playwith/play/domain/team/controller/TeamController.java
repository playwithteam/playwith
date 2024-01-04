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


    //팀 목록
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

    //팀 신청
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/team_list/{teamId}")
    @ResponseBody
    public ResponseEntity<?> applyToTeam(@PathVariable(name = "teamId") Long teamId, Principal principal) {
        Team team = this.teamService.getTeam(teamId);
        SiteUser siteUser = this.userService.findByUsername(principal.getName());

        if (siteUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용");
        }
        teamService.applyToTeam(team.getId(), siteUser);
        return ResponseEntity.ok("팀 가입 성공");
    }

    //팀 생성
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team_create")
    public String team_create(TeamCreateForm teamCreateForm) {
        return "team_create";
    }

    //팀 조회
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/team_detail/{id}")
    public ResponseEntity<?> team_create(@Valid TeamCreateForm teamCreateForm, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("BindingResult error");
        }
        SiteUser siteUser = this.userService.findByUsername(principal.getName());
        siteUser.setRating(2);
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

    //팀 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team_modify/{id}")
    public String team_modify(Model model, TeamCreateForm teamCreateForm, @PathVariable("id") Long id, Principal principal) {
        Team team = this.teamService.getTeam(id);

        if (team != null) {
            SiteUser siteUser = this.userService.findByUsername(principal.getName());
            siteUser.setRating(2);
//            MultipartFile profileImage = teamCreateForm.getProfileImage();

//            teamCreateForm.setProfileImage(profileImage);
            teamCreateForm.setTeamName(team.getTeamName());
            teamCreateForm.setArea(team.getArea());
            teamCreateForm.setLevel(team.getLevel());
            teamCreateForm.setSiteUser(siteUser);

            model.addAttribute("team", team);
            model.addAttribute("teamCreateForm", teamCreateForm);
        }
        return "team_modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/team_modify/{id}")
    @ResponseBody
    public ResponseEntity<String> team_modify(@Valid TeamCreateForm teamCreateForm, BindingResult bindingResult,
                                              Principal principal, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("입력된 값이 올바르지 않습니다.");
        }

        Team team = this.teamService.getTeam(id);
        if (team == null) {
            return ResponseEntity.notFound().build();
        }

        SiteUser siteUser = this.userService.findByUsername(principal.getName());
        siteUser.setRating(2);

        try {
            // 수정된 팀 정보를 데이터베이스에 반영
            this.teamService.modifyTeam(team, teamCreateForm.getProfileImage(), teamCreateForm.getTeamName(), teamCreateForm.getArea(), teamCreateForm.getLevel(),siteUser);
            return ResponseEntity.ok("팀 수정이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("팀 수정 중 오류가 발생했습니다.");
        }
    }

    // 팀 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team_delete/{id}")
    public String team_delete(Model model, @PathVariable("id") Long id) {
        Team team = this.teamService.getTeam(id);

        if (team == null || !team.getId().equals(id)) {
            // 요청된 팀 ID와 DB에서 가져온 팀 ID가 일치하지 않을 경우 에러 처리
            return "error";
        }

        // 팀 삭제 시 연결된 사용자의 팀 외래 키를 null로 설정
        this.teamService.deleteTeamWithNullifyingUsers(id);

        model.addAttribute("team", team);
        this.teamService.delete(team);
        return "redirect:/team/team_list";
    }

    //팀 가입시, 팀명 중복체크
    @GetMapping("/check_teamname")
    @ResponseBody
    public ResponseEntity<String> check_teamname(@RequestParam("teamName") String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("팀명을 다시 입력하세요.");
        }
        if (teamService.isTeamNameUnique(teamName)) {
            return ResponseEntity.ok("사용 가능한 팀명입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 팀명입니다.");
        }
    }

    @GetMapping("/check_modifyteamname")
    @ResponseBody
    public ResponseEntity<String> check_modifyteamname(@RequestParam("teamName") String teamName) {
        if ((teamName == null || teamName.trim().isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("팀명을 다시 입력하세요.");
        }
        if (teamService.isTeamNameUnique(teamName)) {
            return ResponseEntity.ok("사용 가능한 팀명입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 팀명입니다.");
        }
    }
}