package com.playwith.play.domain.matching.service;

import com.playwith.play.domain.matching.controller.MatchingForm;
import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matching.entity.MatchingType;
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

    public void create(MatchingType matchingType, LocalDate gameDate, LocalTime gameTime, String level, String area, String betel) {
        Matching matching = Matching
                .builder()
                .matchingType(matchingType)
                .gameDate(gameDate)
                .gameTime(gameTime)
                .level(level)
                .area(area)
                .betel(betel)
                .createdDate(LocalDateTime.now())
                .build();
        this.matchingRepository.save(matching);
    }
}
