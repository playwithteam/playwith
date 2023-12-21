package com.playwith.play.domain.qna.controller;

import com.playwith.play.domain.qna.entity.Qna;
import com.playwith.play.domain.qna.service.QnaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Qna> qnaList = this.qnaService.getList();
        model.addAttribute("qnaList", qnaList);
        return "qna_list";
    }

    @GetMapping("/create")
    public String create(QnaForm qnaForm) {
        return "qna_form";
    }

    @PostMapping("/create")
    public String create(@Valid QnaForm qnaForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "qna_form";
        }
        this.qnaService.create(qnaForm.getTitle(),qnaForm.getContent());
        return "redirect:/qna/list";
    }

//    @GetMapping("/modify/{id}")
//    public String modify(QnaForm qnaForm, @PathVariable("id") Long id) {
//        Qna qna = this.qnaService.getQna(id);
//        qnaForm.setTitle(qna.getTitle());
//        qnaForm.setContent(qna.getContent());
//        return "qna_form";
//    }
//
//    @PostMapping("/modify/{id}")
//    public String modify(@Valid QnaForm qnaForm, BindingResult bindingResult, @PathVariable("id") Long id) {
//        if (bindingResult.hasErrors()) {
//            return "qna_form";
//        }
//        Qna qna = this.qnaService.getQna(id);
//        this.qnaService.modify(qna, qnaForm.getTitle(), qnaForm.getContent());
//        return "redirect:/qna/list";
//    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Qna qna = this.qnaService.getQna(id);
        this.qnaService.delete(qna);
        return "redirect:/qna/list";
    }

    @GetMapping("/deleteAll")
    public String deleteAll() {
        List<Qna> qnaList = this.qnaService.getList();
        this.qnaService.deleteAll(qnaList);
        return "redirect:/qna/list";
    }

    @PostMapping("/deleteSelectedQna")
    @ResponseBody
    public String deleteSelectedQna(@RequestBody Map<String, String[]> requestBody) {
        String[] temp = requestBody.get("selectedIds");
        qnaService.deleteSelectedQna(temp);
        return "redirect:/qna/list";
    }
}
