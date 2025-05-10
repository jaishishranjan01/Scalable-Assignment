package com.etrain.trainservice.dto;

import com.etrain.trainservice.entity.Passenger;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    private Long trainId;
    private List<PassengerRequest> passengers;
    private boolean paymentSuccess;
}
