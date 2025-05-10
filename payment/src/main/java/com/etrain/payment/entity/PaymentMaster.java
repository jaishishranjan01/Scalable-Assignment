package com.etrain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private String paymentMethod;
    private Double totalAmount;
    private LocalDateTime createdAt;

    @PrePersist
    public void init() {
        this.transactionId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
}
