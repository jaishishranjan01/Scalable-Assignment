package com.etrain.payment.repository;

import com.etrain.payment.entity.PaymentUpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentUpiRepository extends JpaRepository<PaymentUpi, Long> {

}
