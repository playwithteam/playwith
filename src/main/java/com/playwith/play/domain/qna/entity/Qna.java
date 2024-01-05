package com.playwith.play.domain.qna.entity;

import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@ToString
public class Qna extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

}
