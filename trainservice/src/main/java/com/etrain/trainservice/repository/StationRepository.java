package com.etrain.trainservice.repository;

import com.etrain.trainservice.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {
    List<Station> findByStationNameContainingIgnoreCase(String stationName);
}
