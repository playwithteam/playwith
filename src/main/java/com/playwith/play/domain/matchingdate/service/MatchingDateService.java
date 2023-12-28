package com.playwith.play.domain.matchingdate.service;

import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matchingdate.entity.MatchingDate;
import com.playwith.play.domain.matchingdate.repository.MatchingDateRepository;
import com.playwith.play.global.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingDateService {
    private final MatchingDateRepository matchingDateRepository;

    public void create(LocalDate gameDate) {
        MatchingDate matchingDate = MatchingDate
                .builder()
                .gameDate(gameDate)
                .createdDate(LocalDateTime.now())
                .build();
        this.matchingDateRepository.save(matchingDate);
    }

    public List<MatchingDate> getList() {
        return this.matchingDateRepository.findAll();
    }

    public MatchingDate getMatchingDate(LocalDate gameDate) {
        return matchingDateRepository.findByGameDate(gameDate)
                .orElseThrow(() -> new DataNotFoundException("MatchingDate not found"));
    }
}
