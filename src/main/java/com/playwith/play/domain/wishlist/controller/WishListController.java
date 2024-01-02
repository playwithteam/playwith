package com.playwith.play.domain.wishlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WishListController {

    @GetMapping("/wishlist")
    public String wishlist() {
        return "wishlist";
    }
}
