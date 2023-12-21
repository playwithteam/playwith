package com.playwith.play.domain.reportarticle.entity;

import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class ReportArticle extends BaseEntity {
    private String title;
    private String content;
    @ManyToOne
    private SiteUser siteUser;
}
