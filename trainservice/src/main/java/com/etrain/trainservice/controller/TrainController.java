package com.etrain.trainservice.controller;

import com.etrain.trainservice.client.AuthServiceClient;
import com.etrain.trainservice.dto.BookingRequest;
import com.etrain.trainservice.dto.BookingResponse;
import com.etrain.trainservice.dto.TrainSearchRequest;
import com.etrain.trainservice.entity.Ticket;
import com.etrain.trainservice.entity.Train;
import com.etrain.trainservice.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

    private final TrainService trainService;
    private final AuthServiceClient authServiceClient;

    TrainController (TrainService trainService, AuthServiceClient authServiceClient) {
        this.trainService = trainService;
        this.authServiceClient = authServiceClient;
    }

    @PostMapping("/search")
    public ResponseEntity<List<Train>> searchTrains(
            @RequestHeader("Authorization") String token,
            @RequestBody TrainSearchRequest request) {

        if (!authServiceClient.validateToken(token)) { //validate token manually
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(trainService.searchTrains(request));
    }

    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookTicket(
            @RequestHeader("Authorization") String token,
            @RequestBody BookingRequest request) {

        if (!authServiceClient.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(trainService.bookTicket(request));
    }

    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<Ticket> getTicketByPnr(
            @RequestHeader("Authorization") String token,
            @PathVariable String pnr) {

        if (!authServiceClient.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(trainService.getTicketByPnr(pnr));
    }


}
