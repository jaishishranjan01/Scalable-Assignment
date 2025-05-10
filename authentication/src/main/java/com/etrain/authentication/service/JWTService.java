package com.etrain.authentication.service;

import org.springframework.stereotype.Service;

@Service
public interface JWTService {
    public boolean isTokenValid(String token);
    public String extractEmail(String token);
    public String generateToken(String email);
}
