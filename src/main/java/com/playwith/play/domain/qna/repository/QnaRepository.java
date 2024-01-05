package com.playwith.play.domain.qna.repository;

import com.playwith.play.domain.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

}
