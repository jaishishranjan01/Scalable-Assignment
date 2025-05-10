package com.etrain.trainservice.service.impl;

import com.etrain.trainservice.dto.BookingRequest;
import com.etrain.trainservice.dto.BookingResponse;
import com.etrain.trainservice.dto.PassengerRequest;
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
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TicketRepository ticketRepository;

    public TrainServiceImpl(TrainRepository trainRepository, TicketRepository ticketRepository) {
        this.trainRepository = trainRepository;
        this.ticketRepository = ticketRepository;
    }

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

        Ticket ticket = new Ticket();
        ticket.setPnrNumber(TrainUtils.generatePnrNumber());
        ticket.setTrainId(train.getId());
        ticket.setTrainName(train.getTrainName());
        ticket.setSourceStation(train.getSourceStation());
        ticket.setDestinationStation(train.getDestinationStation());
        ticket.setJourneyDate(LocalDate.now());
        ticket.setTransactionId("TXN" + System.currentTimeMillis());
        ticket.setNumberOfSeats(numberOfSeats);
        ticket.setTotalFare(totalFare);
        ticket.setBookingStatus("Confirmed");


        Ticket savedTicket = ticketRepository.save(ticket);

        List<Passenger> passengers = new ArrayList<>();

        for (PassengerRequest p : request.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(p.getName());
            passenger.setAge(p.getAge());
            passenger.setGender(p.getGender());
            passenger.setTicket(savedTicket);

            passengers.add(passenger);
        }


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
