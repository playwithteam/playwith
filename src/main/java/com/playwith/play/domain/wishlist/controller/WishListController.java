package com.playwith.play.domain.wishlist.controller;

import com.playwith.play.domain.wishlist.entity.WishList;
import com.playwith.play.domain.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class WishListController {

    private final WishListService wishListService;

    @GetMapping("/wishlist")
    public String wishlist() {
        return "wishlist";
    }

    @ResponseBody
    @PostMapping("/toggleFavorite/{matchingId}")
    public ResponseEntity<String> toggleFavorite(@PathVariable(name = "matchingId") Long matchingId, Principal principal) {
        String username = principal.getName();
        wishListService.toggleFavorite(matchingId, username);
        return ResponseEntity.ok("Toggle successful");
    }

    @ResponseBody
    @GetMapping("/wishlists")
    public ResponseEntity<List<WishList>> getWishList(Principal principal) {
        String username = principal.getName();
        List<WishList> wishList = wishListService.getWishListByUsername(username);
        return ResponseEntity.ok(wishList);
    }
}
