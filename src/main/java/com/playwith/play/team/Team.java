package com.playwith.play.team;

import com.playwith.play.teamArticle.TeamArticle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@Entity
@Setter
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private String area;
    private String ageGroup;
    private String teamLevel;

    @OneToMany
    private List<TeamArticle> teamArticle;
}
