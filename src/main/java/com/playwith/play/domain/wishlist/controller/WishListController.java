package com.playwith.play.domain.wishlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class WishListController {
    @GetMapping("/wishlist")
    public String wishlist() {
        return "wishlist";
    }
}
