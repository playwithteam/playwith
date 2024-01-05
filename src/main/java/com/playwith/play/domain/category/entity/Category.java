package com.playwith.play.domain.category.entity;

import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class Category extends BaseEntity {
    private String name;

}
