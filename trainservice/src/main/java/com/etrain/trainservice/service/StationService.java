package com.etrain.trainservice.service;

import com.etrain.trainservice.entity.Station;

import java.util.List;

public interface StationService {
    List<Station> searchStations(String query);
}
