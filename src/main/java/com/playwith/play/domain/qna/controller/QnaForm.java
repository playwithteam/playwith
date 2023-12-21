package com.playwith.play.domain.qna.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaForm {
    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String title;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String content;
}
