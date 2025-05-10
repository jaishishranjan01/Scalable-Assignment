package com.etrain.authentication.service.impl;

import com.etrain.authentication.dto.LoginRequest;
import com.etrain.authentication.dto.SignupRequest;
import com.etrain.authentication.entity.User;
import com.etrain.authentication.repository.UserRepository;
import com.etrain.authentication.service.AuthService;
import com.etrain.authentication.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public String signup(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(signupRequest.getName())
                .age(signupRequest.getAge())
                .mobile(signupRequest.getMobile())
                .email(signupRequest.getEmail())
                .address(signupRequest.getAddress())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();

        userRepository.save(user);
        return "Signup successful!";
    }

    public Map<String, String> login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        Map<String, String> token = new HashMap<>();
        token.put("token", jwtService.generateToken(user.getEmail()));
        return token;
    }

    @Override
    public boolean isTokenValid(String jwt) {
        return jwtService.isTokenValid(jwt);
    }
}
