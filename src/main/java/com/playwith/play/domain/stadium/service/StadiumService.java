package com.playwith.play.domain.stadium.service;

import com.playwith.play.domain.stadium.entity.Stadium;
import com.playwith.play.domain.stadium.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;

    public void create(String area, String name, String address, String mapUrl) {
        Stadium stadium = Stadium
                .builder()
                .area(area)
                .name(name)
                .address(address)
                .mapUrl(mapUrl)
                .build();
        this.stadiumRepository.save(stadium);
    }

    public List<Stadium> getStadiumsByArea(String area) {
        return  this.stadiumRepository.getStadiumsByArea(area);
    }

    public Stadium getStadiumsByName(String betel) {
        return  this.stadiumRepository.getStadiumsByName(betel);
    }
}
