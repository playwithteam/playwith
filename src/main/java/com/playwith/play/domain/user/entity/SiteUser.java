package com.playwith.play.domain.user.entity;

import com.playwith.play.domain.reportarticle.entity.ReportArticle;
import com.playwith.play.domain.soldierarticle.entity.SoldierArticle;
import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.wishlist.entity.WishList;
import com.playwith.play.global.jpa.BaseEntity;
import com.playwith.play.global.jpa.BaseEntity;
import groovyjarjarpicocli.CommandLine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@ToString
@Entity
public class SiteUser extends BaseEntity {

    @Column(unique = true)
    @Comment("유저 아이디")
    private String username;

    @Comment("이름")
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
    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // 모든 멤버는 member 권한을 가진다.
        grantedAuthorities.add(new SimpleGrantedAuthority("user"));
        // username이 admin인 회원은 추가로 admin 권한도 가진다.
        if (isAdmin()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }
        return grantedAuthorities;
    }
    public boolean isAdmin() {
        return "admin".equals(username);
    }

}
