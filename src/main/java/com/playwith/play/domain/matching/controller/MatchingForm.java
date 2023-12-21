package com.playwith.play.domain.matching.controller;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class MatchingForm {
    @NotNull(message = "필수 입력 항목 입니다.")
    private String matchingType;

    @NotNull(message = "필수 입력 항목 입니다.")
    private LocalDate createDate;

    @NotNull(message = "필수 입력 항목 입니다.")
    private LocalTime createTime;

    @NotBlank(message = "필수 입력 항목 입니다.")
    private String level;

    @NotBlank(message = "필수 입력 항목 입니다.")
    private String area;

    @NotBlank(message = "필수 입력 항목 입니다.")
    private String betel;
}
