package com.playwith.play.domain.wishlist.service;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matching.repository.MatchingRepository;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.repository.UserRepository;
import com.playwith.play.domain.wishlist.entity.WishList;
import com.playwith.play.domain.wishlist.repository.WishListRepository;
import com.playwith.play.global.util.DataNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final MatchingRepository matchingRepository;
    private final UserRepository userRepository;

    public List<WishList> getWishListByUsername(String username) {
        return wishListRepository.findBySiteUserUsername(username);
    }

    @Transactional
    public void toggleFavorite(Long matchingId, String username) {
        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new RuntimeException("Matching not found with id: " + matchingId));

        WishList wishList = wishListRepository.findBySiteUserUsernameAndMatchingId(username, matchingId);
        if (wishList != null) {
            // Remove matching from wish list
            wishListRepository.delete(wishList);

            // Remove wish list from matching
            matching.getWishLists().remove(wishList);
        } else {
            SiteUser siteUser = this.userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("siteuser not found"));

            wishList = new WishList();
            wishList.setSiteUser(siteUser);
            wishList.setMatching(matching); // Set matching relationship
            wishListRepository.save(wishList);

            // Add wish list to matching
            matching.getWishLists().add(wishList);
        }
    }

}
