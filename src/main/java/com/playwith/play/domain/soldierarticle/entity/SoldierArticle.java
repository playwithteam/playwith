package com.playwith.play.domain.soldierarticle.entity;

import com.playwith.play.domain.appInformation.entity.AppInformation;
import com.playwith.play.domain.article.entity.Article;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class SoldierArticle extends BaseEntity {

    @ManyToOne
    private Article article;
    @ManyToOne
    private SiteUser siteUser;

    @OneToMany
    private List<AppInformation> appInformationList;
}
