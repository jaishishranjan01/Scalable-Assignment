package com.etrain.trainservice.controller;

import com.etrain.trainservice.entity.Station;
import com.etrain.trainservice.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains/stations")
@CrossOrigin(origins = "http://localhost:3000")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Station>> searchStations(@RequestParam String query) {
        List<Station> stations = stationService.searchStations(query);
        return ResponseEntity.ok(stations);
    }
}
