package com.etrain.trainservice.service;

import com.etrain.trainservice.dto.BookingRequest;
import com.etrain.trainservice.dto.BookingResponse;
import com.etrain.trainservice.dto.TrainSearchRequest;
import com.etrain.trainservice.entity.Ticket;
import com.etrain.trainservice.entity.Train;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainService {
    List<Train> searchTrains(TrainSearchRequest request);

    BookingResponse bookTicket(BookingRequest request);

    Ticket getTicketByPnr(String pnr);
}
