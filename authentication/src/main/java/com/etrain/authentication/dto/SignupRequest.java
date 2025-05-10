package com.etrain.authentication.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private Integer age;
    private String mobile;
    private String email;
    private String address;
    private String password;
}