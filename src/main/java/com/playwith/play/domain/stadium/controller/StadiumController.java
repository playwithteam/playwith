package com.playwith.play.domain.stadium.controller;

import com.playwith.play.domain.stadium.entity.Stadium;
import com.playwith.play.domain.stadium.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stadium")
public class StadiumController {
    private final StadiumService stadiumService;

    @GetMapping("/stadiumList")
    @ResponseBody
    public List<Stadium> getStadiumsByArea(@RequestParam("area") String area) {
        return stadiumService.getStadiumsByArea(area);
    }
}
