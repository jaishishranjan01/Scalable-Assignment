package com.etrain.authentication.service.impl;

import com.etrain.authentication.dto.LoginRequest;
import com.etrain.authentication.dto.SignupRequest;
import com.etrain.authentication.entity.User;
import com.etrain.authentication.repository.UserRepository;
import com.etrain.authentication.service.AuthService;
import com.etrain.authentication.service.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String signup(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setAge(signupRequest.getAge());
        user.setMobile(signupRequest.getMobile());
        user.setEmail(signupRequest.getEmail());
        user.setAddress(signupRequest.getAddress());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));


        userRepository.save(user);
        return "Signup successful!";
    }

    @Override
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
