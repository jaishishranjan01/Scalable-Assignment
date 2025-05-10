package com.etrain.payment.controller;

import com.etrain.payment.client.AuthServiceClient;
import com.etrain.payment.dto.CardPaymentRequest;
import com.etrain.payment.dto.UpiPaymentRequest;
import com.etrain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment/api")
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthServiceClient authClient;

    public PaymentController(PaymentService paymentService, AuthServiceClient authClient) {
        this.paymentService = paymentService;
        this.authClient = authClient;
    }

    @PostMapping("/upi")
    public ResponseEntity<?> upi(@RequestHeader("Authorization") String token,
                                 @RequestBody UpiPaymentRequest request) {
        if (!authClient.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        paymentService.processUpi(request);
        return ResponseEntity.ok("UPI Payment Processed");
    }

    @PostMapping("/card")
    public ResponseEntity<?> card(@RequestHeader("Authorization") String token,
                                  @RequestBody CardPaymentRequest request) {
        if (!authClient.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        paymentService.processCard(request);
        return ResponseEntity.ok("Card Payment Processed");
    }
}

