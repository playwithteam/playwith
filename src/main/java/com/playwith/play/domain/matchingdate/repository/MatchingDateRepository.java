package com.playwith.play.domain.matchingdate.repository;

import com.playwith.play.domain.matchingdate.entity.MatchingDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MatchingDateRepository extends JpaRepository<MatchingDate, Long> {
    Optional<MatchingDate> findByGameDate(LocalDate gameDate);
}
