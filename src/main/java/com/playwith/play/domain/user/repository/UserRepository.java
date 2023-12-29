package com.playwith.play.domain.user.repository;

import com.playwith.play.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByEmailAndName(String eamil, String name);

    Optional<SiteUser> findByUsernameAndEmailAndName(String username, String Email, String name);

    Optional<SiteUser> findByUsername(String username);

    List<SiteUser> findByTeamId(Long teamId);

    boolean existsByUsername(String username);
}
