package com.etrain.payment.service;


import com.etrain.payment.dto.CardPaymentRequest;
import com.etrain.payment.dto.UpiPaymentRequest;
import com.etrain.payment.entity.PaymentCard;
import com.etrain.payment.entity.PaymentMaster;
import com.etrain.payment.entity.PaymentUpi;
import com.etrain.payment.repository.PaymentCardRepository;
import com.etrain.payment.repository.PaymentMasterRepository;
import com.etrain.payment.repository.PaymentUpiRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentMasterRepository masterRepo;
    private final PaymentUpiRepository upiRepo;
    private final PaymentCardRepository cardRepo;

    public PaymentService(PaymentMasterRepository masterRepo, PaymentUpiRepository upiRepo, PaymentCardRepository cardRepo) {
        this.masterRepo = masterRepo;
        this.upiRepo = upiRepo;
        this.cardRepo = cardRepo;
    }

    public void processUpi(UpiPaymentRequest req) {
        PaymentMaster master = new PaymentMaster();
        master.setPaymentMethod("UPI");
        master.setTotalAmount(req.getTotalAmount());
        masterRepo.save(master);

        PaymentUpi upi = new PaymentUpi();
        upi.setMasterId(master.getId());
        upi.setUpiId(req.getUpiId());
        upiRepo.save(upi);
    }

    public void processCard(CardPaymentRequest req) {
        PaymentMaster master = new PaymentMaster();
        master.setPaymentMethod("Card");
        master.setTotalAmount(req.getTotalAmount());
        masterRepo.save(master);

        PaymentCard card = new PaymentCard();
        card.setMasterId(master.getId());
        card.setCardNumber(req.getCardNumber());
        card.setExpiryMonth(req.getExpiryMonth());
        card.setExpiryYear(req.getExpiryYear());
        card.setCvv(req.getCvv());
        card.setCardHolderName(req.getCardHolderName());
        cardRepo.save(card);
    }
}

