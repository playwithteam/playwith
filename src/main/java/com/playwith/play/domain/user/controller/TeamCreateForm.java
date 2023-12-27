package com.playwith.play.domain.user.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamCreateForm {
    @NotNull(message = "필수 입력 항목 입니다.")
    @Size(min = 1, max = 8)
    private String teamName;
    @NotNull(message = "필수 입력 항목 입니다.")
    private String area;
    @NotNull(message = "필수 입력 항목 입니다.")
    private String level;
}
