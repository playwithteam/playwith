package com.playwith.play.domain.team.controller;

import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.user.entity.SiteUser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TeamCreateForm {

    private MultipartFile profileImage;

    @Size(min = 1, max = 8, message = "팀명은 1자 ~ 8자를 사용하세요.")
    private String teamName;
    @NotNull(message = "필수 입력 항목 입니다.")
    private String area;
    @NotNull(message = "필수 입력 항목 입니다.")
    private String level;
    private SiteUser siteUser;
    private Team team = new Team();
}
