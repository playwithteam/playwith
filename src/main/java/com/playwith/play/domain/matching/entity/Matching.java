package com.playwith.play.domain.matching.entity;

import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
}