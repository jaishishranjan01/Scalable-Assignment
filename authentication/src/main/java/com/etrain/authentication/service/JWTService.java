package com.etrain.authentication.service;

public interface JWTService {
    public boolean isTokenValid(String token);
    public String extractEmail(String token);
    public String generateToken(String email);
}
