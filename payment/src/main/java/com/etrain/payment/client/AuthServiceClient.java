package com.etrain.payment.client;

import com.etrain.payment.controller.PaymentController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthServiceClient {
    private final WebClient webClient;

    private static final Logger log = LoggerFactory.getLogger(AuthServiceClient.class);

    public AuthServiceClient(@Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(authServiceUrl).build();
    }

    public boolean validateToken(String token) {
        try {
            return true;
//            log.info("auth url: {}", webClient.get() );
//            return Boolean.TRUE.equals(webClient.get()
//                    .uri("/api/auth/validate")
//                    .header("Authorization", token)
//                    .retrieve()
//                    .bodyToMono(Boolean.class)
//                    .block());
        } catch (Exception e) {
            log.error("excpetion : "+e);
            return false;
        }
    }
}
