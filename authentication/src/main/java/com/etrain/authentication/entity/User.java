package com.etrain.authentication.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "users", schema = "etrain")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String mobile;
    private String email;
    private String address;
    private String password;
}
