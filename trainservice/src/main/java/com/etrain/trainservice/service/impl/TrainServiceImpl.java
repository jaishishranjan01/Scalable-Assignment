package com.etrain.trainservice.service.impl;

import com.etrain.trainservice.dto.BookingRequest;
import com.etrain.trainservice.dto.BookingResponse;
import com.etrain.trainservice.dto.TrainSearchRequest;
import com.etrain.trainservice.entity.Passenger;
import com.etrain.trainservice.entity.Ticket;
import com.etrain.trainservice.entity.Train;
import com.etrain.trainservice.repository.TicketRepository;
import com.etrain.trainservice.repository.TrainRepository;
import com.etrain.trainservice.service.TrainService;
import com.etrain.trainservice.util.TrainUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<Train> searchTrains(TrainSearchRequest request) {
        return trainRepository.searchTrain(
                request.getSourceStation(), request.getDestinationStation());
    }

    @Override
    @Transactional
    public BookingResponse bookTicket(BookingRequest request) {
        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found!"));

        if (train.getSourceStation().getId().equals(train.getDestinationStation().getId())) {
            throw new IllegalArgumentException("Source and Destination stations cannot be the same!");
        }

        if (!request.isPaymentSuccess()) {
            throw new RuntimeException("Payment failed. Ticket not booked.");
        }

        int numberOfSeats = request.getPassengers().size();

        if (train.getAvailableSeats() < numberOfSeats) {
            throw new RuntimeException("Not enough seats available!");
        }

        double totalFare = train.getFare() * numberOfSeats;

        Ticket ticket = Ticket.builder()
                .pnrNumber(TrainUtils.generatePnrNumber())
                .trainId(train.getId())
                .trainName(train.getTrainName())
                .sourceStation(train.getSourceStation())
                .destinationStation(train.getDestinationStation())
                .journeyDate(LocalDate.now())
                .transactionId("TXN" + System.currentTimeMillis())
                .numberOfSeats(numberOfSeats)
                .totalFare(totalFare)
                .bookingStatus("Confirmed")
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);

        List<Passenger> passengers = request.getPassengers().stream()
                .map(p -> Passenger.builder()
                        .name(p.getName())
                        .age(p.getAge())
                        .gender(p.getGender())
                        .ticket(savedTicket)
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));

        savedTicket.setPassengers(passengers);

        ticketRepository.save(savedTicket);

        train.setAvailableSeats(train.getAvailableSeats() - numberOfSeats);
        trainRepository.save(train);

        return new BookingResponse("Booking successful! Your PNR: " + savedTicket.getPnrNumber(), savedTicket.getPnrNumber());
    }

    @Override
    public Ticket getTicketByPnr(String pnr) {
        return ticketRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new RuntimeException("PNR not found"));
    }
}
