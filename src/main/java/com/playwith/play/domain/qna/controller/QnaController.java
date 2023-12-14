package com.playwith.play.domain.qna.controller;

import com.playwith.play.domain.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;

    @GetMapping("/list")
    public String list() {
        return "qna_list";
    }

    @GetMapping("/form")
    public String create() {
        return "qna_form";
    }


}
