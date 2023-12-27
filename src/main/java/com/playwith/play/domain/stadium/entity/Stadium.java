package com.playwith.play.domain.stadium.entity;

import com.playwith.play.domain.article.entity.Article;
import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.Area;

import java.util.List;

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
    @Column(columnDefinition = "TEXT")
    private String mapUrl;
    @OneToMany
    private List<Matching> matchingList;
}
