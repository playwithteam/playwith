package com.playwith.play.domain.wishlist.repository;

import com.playwith.play.domain.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findBySiteUser_Id(Long userId);
}
