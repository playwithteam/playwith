package com.playwith.play.domain.team.entity;

import com.playwith.play.domain.taemarticle.entity.TeamArticle;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Team extends BaseEntity {
    private String teamName;
    private String area;
    private String ageGroup;
    private String teamLevel;

    @OneToMany
    private List<TeamArticle> teamArticleList;

    @OneToOne
    private SiteUser siteUser;
}
