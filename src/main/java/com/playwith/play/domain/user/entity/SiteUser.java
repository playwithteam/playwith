package com.playwith.play.domain.user.entity;

import com.playwith.play.domain.reportarticle.entity.ReportArticle;
import com.playwith.play.domain.soldierarticle.entity.SoldierArticle;
import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.wishlist.entity.WishList;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Entity
@SuperBuilder
public class SiteUser extends BaseEntity {

    @Column(unique = true)
    private String username;

    private String name;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(unique = true)
    private String email;
    private String area;
    private String level;
    private String nickname;
    private String profileImgUrl;
    private String rating;

    @OneToMany
    private List<ReportArticle> reportArticleList;
    @OneToMany
    private List<SoldierArticle> soldierArticleList;
    @OneToMany
    private List<WishList> wishLists;
    public boolean isSocialMember() {
        return username.startsWith("KAKAO_");
    }  //사용자명이 카카오로 시작하는지 확인

}
