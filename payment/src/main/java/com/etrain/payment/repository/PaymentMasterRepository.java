package com.etrain.payment.repository;

import com.etrain.payment.entity.PaymentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMasterRepository extends JpaRepository<PaymentMaster, Long> {
}
