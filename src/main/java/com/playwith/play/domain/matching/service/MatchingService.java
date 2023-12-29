package com.playwith.play.domain.matching.service;

import com.playwith.play.domain.matching.controller.MatchingForm;
import com.playwith.play.domain.matching.entity.Matching;
import com.playwith.play.domain.matching.entity.MatchingType;
import com.playwith.play.domain.matching.repository.MatchingRepository;
import com.playwith.play.domain.matchingdate.entity.MatchingDate;
import com.playwith.play.domain.stadium.entity.Stadium;
import com.playwith.play.domain.stadium.repository.StadiumRepository;
import com.playwith.play.global.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;

    public void create(MatchingType matchingType, MatchingDate matchingDate, LocalTime gameTime, String level, String area, Stadium stadium, String managerName) {
        Matching matching = Matching
                .builder()
                .matchingType(matchingType)
                .matchingDate(matchingDate)
                .gameTime(gameTime)
                .level(level)
                .area(area)
                .createdDate(LocalDateTime.now())
                .stadium(stadium)
                .managerName(managerName)
                .build();
        this.matchingRepository.save(matching);
    }

    public List<Matching> getList() {
        return this.matchingRepository.findAll();
    }

    public Matching getMatching(Long id) {
        Optional<Matching> matching = this.matchingRepository.findById(id);
        if (matching.isPresent()) {
            return matching.get();
        }
        else {
            throw new DataNotFoundException("matching not found");
        }
    }
}
