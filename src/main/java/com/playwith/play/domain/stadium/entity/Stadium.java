package com.playwith.play.domain.stadium.entity;

import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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
    @Column(columnDefinition = "text")
    private String mapUrl;
}
