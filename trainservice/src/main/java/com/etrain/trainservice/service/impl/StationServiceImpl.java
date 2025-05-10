package com.etrain.trainservice.service.impl;

import com.etrain.trainservice.entity.Station;
import com.etrain.trainservice.repository.StationRepository;
import com.etrain.trainservice.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public List<Station> searchStations(String query) {
        return stationRepository.findByStationNameContainingIgnoreCase(query);
    }
}
