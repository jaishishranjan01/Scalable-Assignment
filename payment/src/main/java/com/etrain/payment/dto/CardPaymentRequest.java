package com.etrain.payment.dto;

import lombok.Data;

@Data
public class CardPaymentRequest {
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;
    private String cardHolderName;
    private Double totalAmount;
}
