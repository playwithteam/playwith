package com.playwith.play.domain.team.entity;

import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@ToString
public class Team extends BaseEntity {

    private String profileImgUrl;

    @Column(unique = true)
    private String teamName;
    private String area;
    private String level;


    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})

    private List<SiteUser> siteUsers = new ArrayList<>();

    public void addMember(SiteUser user) {
        if (this.siteUsers == null) {
            this.siteUsers = new ArrayList<>();
        }
        this.siteUsers.add(user);
        user.setTeam(this);
    }
}
