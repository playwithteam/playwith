package com.playwith.play.domain.matching.controller;

import com.playwith.play.domain.matching.entity.MatchingType;
import com.playwith.play.domain.stadium.entity.Stadium;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class MatchingForm {
    @NotNull(message = "필수 입력 항목 입니다.")
    private MatchingType matchingType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "필수 입력 항목 입니다.")
    private LocalDate gameDate;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "필수 입력 항목 입니다.")
    private LocalTime gameTime;

    @NotBlank(message = "필수 입력 항목 입니다.")
    private String level;

    @NotBlank(message = "필수 입력 항목 입니다.")
    private String area;

    @NotNull(message = "필수 입력 항목 입니다.")
//    private String betel;
    private Stadium stadium;
}
