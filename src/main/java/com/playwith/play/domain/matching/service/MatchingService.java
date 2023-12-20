package com.playwith.play.domain.matching.service;

import com.playwith.play.domain.matching.controller.MatchingForm;
import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matching.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;

    public void create(String matchingType, LocalDate createDate, LocalTime createTime, String level, String area, String betel) {
        Matching matching = new Matching();
        matching.setMatchingType(matchingType);
        matching.setCreateDate(createDate);
        matching.setCreateTime(createTime);
        matching.setLevel(level);
        matching.setArea(area);
        matching.setBetel(betel);
        matching.setCreateDateTime(LocalDateTime.now());
        this.matchingRepository.save(matching);
    }
}
