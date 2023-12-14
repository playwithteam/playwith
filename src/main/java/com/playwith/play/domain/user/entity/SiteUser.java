package com.playwith.play.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;


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

    public boolean isSocialMember() {
        return username.startsWith("KAKAO_");
    }  //사용자명이 카카오로 시작하는지 확인

}
