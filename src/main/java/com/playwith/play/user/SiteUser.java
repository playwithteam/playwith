package com.playwith.play.user;

import com.playwith.play.admin.Administrator;
import com.playwith.play.reportArticle.ReportArticle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String name;
    private String password;
    private LocalDateTime birthDate;

    @Column(unique = true)
    private String email;
    private String area;
    private String level;
    private String nickname;
    private String profileImgUrl;
    private String rating;


    @ManyToOne
    private Administrator administrator;
    @OneToMany
    private List<ReportArticle> reportArticle;


    public boolean isSocialMember() {
        return username.startsWith("KAKAO_");
    }  //사용자명이 카카오로 시작하는지 확인

}
