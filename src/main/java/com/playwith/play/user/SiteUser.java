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

    private String password;

    private LocalDateTime birthDate;

    @Column(unique = true)
    private String email;

    private String area;
    private String level;

    private String nickname;
    private String profileImgUrl;


    @ManyToOne
    private Administrator administrator;
    @OneToMany
    private List<ReportArticle> reportArticle;




    public boolean isAdmin() {
        return "admin".equals(username);
    }
    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // 모든 멤버는 member 권한을 가진다.
        grantedAuthorities.add(new SimpleGrantedAuthority("member"));
        // username이 admin인 회원은 추가로 admin 권한도 가진다.
        if (isAdmin()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }

        return grantedAuthorities;
    }

    public boolean isSocialMember() {
        return username.startsWith("KAKAO_");
    }

    public boolean isModifyAvailable() {
        return !isSocialMember();
    }

    public String getEmailForPrint() {
        if (isSocialMember()) return "-";
        return email;
    }
}
