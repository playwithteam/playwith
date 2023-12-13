package com.playwith.play.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String title;
    private String price;
    private LocalDateTime createDate;
    private String level;
    private LocalDateTime reservationDate;
    private Time time;
}
