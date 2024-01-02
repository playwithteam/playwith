package com.playwith.play.domain.matchingdate.entity;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@ToString
public class MatchingDate extends BaseEntity {
    @Unique
    private LocalDate gameDate;
    @OneToMany
    private List<Matching> matchingList;
}
