package com.playwith.play.domain.stadium.repository;

import com.playwith.play.domain.stadium.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {

    @Query(value = "SELECT * FROM STADIUM WHERE area = :area", nativeQuery = true)
    List<Stadium> getStadiumsByArea(@Param("area") String area);
}
