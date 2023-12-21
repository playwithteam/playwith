package com.playwith.play.domain.taemarticle.entity;

import com.playwith.play.domain.appInformation.entity.AppInformation;
import com.playwith.play.domain.article.entity.Article;
import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Entity
public class TeamArticle extends BaseEntity {

    @ManyToOne
    private Article article;

    @ManyToOne
    private Team team;

    @OneToMany
    private List<AppInformation> appInformationList;
}
