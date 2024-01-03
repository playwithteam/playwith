package com.playwith.play.domain.matching.entity;

import com.playwith.play.domain.matchingdate.entity.MatchingDate;
import com.playwith.play.domain.stadium.entity.Stadium;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.wishlist.entity.WishList;
import com.playwith.play.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    private String managerName;
    @ManyToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;
    @ManyToOne
    @JoinColumn(name = "matching_date_id")
    private MatchingDate matchingDate;
    @OneToMany(mappedBy = "matching", cascade = CascadeType.REMOVE)
    private List<SiteUser> userList;
    @OneToMany(mappedBy = "matching", cascade = CascadeType.REMOVE)
    private List<WishList> wishLists;
}
