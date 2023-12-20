package com.playwith.play.domain.qna.repository;

import com.playwith.play.domain.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

}
