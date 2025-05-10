package com.etrain.authentication.service;

import com.etrain.authentication.dto.LoginRequest;
import com.etrain.authentication.dto.SignupRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {
    public String signup(SignupRequest signupRequest);
    public Map<String, String> login(LoginRequest loginRequest);

    boolean isTokenValid(String jwt);
}
