package com.playwith.play.domain.user.entity;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.reportarticle.entity.ReportArticle;
import com.playwith.play.domain.soldierarticle.entity.SoldierArticle;
import com.playwith.play.domain.wishlist.entity.WishList;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@ToString
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

    @OneToMany
    private List<ReportArticle> reportArticleList;
    @OneToMany
    private List<SoldierArticle> soldierArticleList;
    @OneToMany
    private List<WishList> wishLists;

//    @ManyToOne
//    private Matching matching;


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
