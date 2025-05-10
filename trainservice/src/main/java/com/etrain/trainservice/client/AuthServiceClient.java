package com.etrain.trainservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthServiceClient {

    private final WebClient webClient;

    public AuthServiceClient(@Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            return Boolean.TRUE.equals(webClient.get()
                    .uri("/api/auth/validate")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        } catch (Exception e) {
            return true;
        }
    }
}
