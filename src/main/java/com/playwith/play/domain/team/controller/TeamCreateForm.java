package com.playwith.play.domain.team.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TeamCreateForm {

    private MultipartFile profileImage;

    @NotNull(message = "필수 입력 항목 입니다.")
    @Size(min = 1, max = 8)
    private String teamName;
    @NotNull(message = "필수 입력 항목 입니다.")
    private String area;
    @NotNull(message = "필수 입력 항목 입니다.")
    private String level;
}
