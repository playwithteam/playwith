package com.playwith.play.domain.stadium.entity;

import com.playwith.play.domain.article.entity.Article;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.Area;

@Entity
public class Stadium extends BaseEntity {
    private String area;
    private String name;
    @ManyToOne
    private Article article;
}
