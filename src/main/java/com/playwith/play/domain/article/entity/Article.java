package com.playwith.play.domain.article.entity;

import com.playwith.play.domain.category.entity.Category;
import com.playwith.play.domain.soldierarticle.entity.SoldierArticle;
import com.playwith.play.domain.stadium.entity.Stadium;
import com.playwith.play.domain.taemarticle.entity.TeamArticle;
import com.playwith.play.domain.wishlist.entity.WishList;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Article extends BaseEntity {
    private String title;
    private String price;
    private String level;
    private LocalDate reservationDate;
    private LocalTime time;

    @OneToMany
    private List<SoldierArticle> soldierArticleList;
    @OneToMany
    private List<TeamArticle> teamArticleList;
    @OneToMany
    private List<Stadium> stadiumList;
    @OneToMany
    private List<WishList> wishLists;
    @ManyToOne
    private Category category;
}
