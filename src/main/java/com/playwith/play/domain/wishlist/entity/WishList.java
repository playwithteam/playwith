package com.playwith.play.domain.wishlist.entity;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class WishList extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser siteUser;

    @ManyToOne
    @JoinColumn(name = "matching_id")
    private Matching matching;
}
