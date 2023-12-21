package com.playwith.play.domain.user.entity;

import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
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
    private LocalDateTime birthDate;

    @Column(unique = true)
    private String email;
    private String nickname;
    private String profileImgUrl;
    private String level;
    private String area;
    //관리자와 매니저 등급
    private Integer rating;

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
