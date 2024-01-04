package com.playwith.play.domain.wishlist.entity;

import com.playwith.play.domain.article.entity.Article;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class WishList extends BaseEntity {


    @ManyToOne
    private Article article;
    @ManyToOne
    private SiteUser siteUser;
}
