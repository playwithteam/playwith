package com.playwith.play.domain.user.repository;

import com.playwith.play.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByEmailAndName(String eamil, String name);
    Optional<SiteUser> findByUsernameAndEmailAndName(String username, String Email, String name);
    Optional<SiteUser> findByUsername(String username);
    boolean existsByUsername(String username);

    // 사용자 팀 정보 업데이트
    @Modifying
    @Query("UPDATE SiteUser u SET u.team.id = :teamId WHERE u.id = :userId")
    void updateUserTeam(@Param("userId") Long userId, @Param("teamId") Long teamId);
}
