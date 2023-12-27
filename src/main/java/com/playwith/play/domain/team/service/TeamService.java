package com.playwith.play.domain.team.service;

import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    //팀 생성
    public void createTeam(String teamName, String area, String level) {
        Team team = Team
                .builder()
                .teamName(teamName)
                .area(area)
                .level(level)
                .build();
        this.teamRepository.save(team);
    }
    public void getTeamList(Long id) {
        this.teamRepository.findById(id);
    }
}
