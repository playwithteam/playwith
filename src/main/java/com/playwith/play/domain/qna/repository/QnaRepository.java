package com.playwith.play.domain.qna.repository;

import com.playwith.play.domain.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
}
