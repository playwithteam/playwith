package com.playwith.play.domain.wishlist.service;

import com.playwith.play.domain.wishlist.entity.WishList;
import com.playwith.play.domain.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;

    public List<WishList> getWishListByUserId(Long userId) {
        return wishListRepository.findBySiteUser_Id(userId);
    }

}
