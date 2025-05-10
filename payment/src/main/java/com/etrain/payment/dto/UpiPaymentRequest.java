package com.etrain.payment.dto;

import lombok.Data;

@Data
public class UpiPaymentRequest {
    private String upiId;
    private Double totalAmount;
}
