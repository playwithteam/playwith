package com.playwith.play.domain.category.entity;

import com.playwith.play.domain.article.entity.Article;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Category extends BaseEntity {
    private String name;

    @OneToMany
    private List<Article> articleList;
}
