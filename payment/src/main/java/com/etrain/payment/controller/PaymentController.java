package com.etrain.payment.controller;

import com.etrain.payment.client.AuthServiceClient;
import com.etrain.payment.dto.CardPaymentRequest;
import com.etrain.payment.dto.UpiPaymentRequest;
import com.etrain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment/api")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthServiceClient authClient;

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService, AuthServiceClient authClient) {
        this.paymentService = paymentService;
        this.authClient = authClient;
    }

    @PostMapping("/upi")
    public ResponseEntity<?> upi(@RequestHeader("Authorization") String token,
                                 @RequestBody UpiPaymentRequest request) {
        log.info("token: {}", token);

//        if (!authClient.validateToken(token)) {
//            log.info("not able to validate token: {}", token);
//            return ResponseEntity.status(401).build();
//        }
        paymentService.processUpi(request);
        return ResponseEntity.ok("UPI Payment Processed");
    }

    @PostMapping("/card")
    public ResponseEntity<?> card(@RequestHeader("Authorization") String token,
                                  @RequestBody CardPaymentRequest request) {
        log.info("token: {}", token);

//        if (!authClient.validateToken(token)) {
//            log.info("not able to validate token: {}", token);
//            return ResponseEntity.status(401).build();
//        }
        paymentService.processCard(request);
        return ResponseEntity.ok("Card Payment Processed");
    }
}

