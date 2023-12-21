package com.playwith.play.domain.matching.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String matchingType;

    private LocalDate createDate;

    private LocalTime createTime;

    @Column(columnDefinition = "TEXT")
    private String level;

    @Column(columnDefinition = "TEXT")
    private String area;

    @Column(columnDefinition = "TEXT")
    private String betel;

    private LocalDateTime createDateTime;

    private LocalDateTime modifyDateTime;

    @Column(columnDefinition = "TEXT")
    private String manager;
}
