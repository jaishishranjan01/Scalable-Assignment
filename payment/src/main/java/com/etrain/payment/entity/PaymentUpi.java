package com.etrain.payment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_upi")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentUpi {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long masterId;
    private String upiId;
}
