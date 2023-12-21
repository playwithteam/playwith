package com.playwith.play.domain.matching.repository;

import com.playwith.play.domain.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
}
