package com.playwith.play.domain.matching.entity;

import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@ToString
public class Matching extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private MatchingType matchingType;
    private LocalDate gameDate;
    private LocalTime gameTime;
    private String level;
    private String area;
    private String betel;
    private String managerName;
//    @ManyToMany(mappedBy = "matching", cascade = CascadeType.REMOVE)
//    private List<SiteUser> siteUserList;
}
