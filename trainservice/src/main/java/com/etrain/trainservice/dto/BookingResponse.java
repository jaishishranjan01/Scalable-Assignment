package com.etrain.trainservice.dto;

import lombok.Data;

@Data
public class BookingResponse {
    public BookingResponse (String message, String pnrNumber) {
        this.message = message;
        this.pnrNumber = pnrNumber;
    }
    private String message;
    private String pnrNumber;
}
