package com.etrain.trainservice.dto;

import lombok.Data;

public class BookingResponse {
    public BookingResponse (String message, String pnrNumber) {
        this.message = message;
        this.pnrNumber = pnrNumber;
    }
    private String message;
    private String pnrNumber;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }
}
