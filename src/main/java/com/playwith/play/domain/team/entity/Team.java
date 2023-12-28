package com.playwith.play.domain.team.entity;

import com.playwith.play.domain.taemarticle.entity.TeamArticle;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@ToString
public class Team extends BaseEntity {
    private String teamName;
    private String area;
    private String level;

    @OneToMany
    private List<TeamArticle> teamArticleList;

    @OneToMany
    private List<SiteUser> siteUserList;
}
