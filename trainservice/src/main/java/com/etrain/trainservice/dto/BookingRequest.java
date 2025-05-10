package com.etrain.trainservice.dto;

import com.etrain.trainservice.entity.Passenger;
import lombok.Data;

import java.util.List;

public class BookingRequest {
    private Long trainId;
    private List<PassengerRequest> passengers;
    private boolean paymentSuccess;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public List<PassengerRequest> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerRequest> passengers) {
        this.passengers = passengers;
    }

    public boolean isPaymentSuccess() {
        return paymentSuccess;
    }

    public void setPaymentSuccess(boolean paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }
}
