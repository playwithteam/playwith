package com.playwith.play.domain.stadium.entity;

import com.playwith.play.domain.article.entity.Article;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.Area;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@ToString
public class Stadium extends BaseEntity {
    private String area;
    private String name;
    private String address;
    private String mapUrl;
}
