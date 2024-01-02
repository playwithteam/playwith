package com.playwith.play.domain.matchingdate.controller;

import com.playwith.play.domain.matchingdate.service.MatchingDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/matchingDate")
@RequiredArgsConstructor
public class MatchingDateController {
    private final MatchingDateService matchingDateService;
}
