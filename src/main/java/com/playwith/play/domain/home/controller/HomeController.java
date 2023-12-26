package com.playwith.play.domain.home.controller;

import com.playwith.play.domain.qna.entity.Qna;
import com.playwith.play.domain.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@EnableJpaAuditing
public class HomeController {
    private final QnaService qnaService;

    @GetMapping("/")
    public String main(Model model) {
        List<Qna> qnaList = this.qnaService.getList();
        model.addAttribute("qnaList", qnaList);
        return "index";
    }

}
