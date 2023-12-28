package com.playwith.play.domain.user.controller;

import com.playwith.play.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HeaderController {

    private final UserService userService;

    @Autowired
    public HeaderController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addProfileImageUrlToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            String profileImgUrl = userService.getProfileImageUrlByUsername(username);
            model.addAttribute("profileImgUrl", profileImgUrl);
        }
    }
}
