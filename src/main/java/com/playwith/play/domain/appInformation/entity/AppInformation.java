package com.playwith.play.domain.appInformation.entity;

import com.playwith.play.domain.soldierarticle.entity.SoldierArticle;
import com.playwith.play.domain.taemarticle.entity.TeamArticle;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@Entity
public class AppInformation extends BaseEntity {
    @ManyToOne
    private TeamArticle teamArticle;
    @ManyToOne
    private SoldierArticle soldierArticle;
}
