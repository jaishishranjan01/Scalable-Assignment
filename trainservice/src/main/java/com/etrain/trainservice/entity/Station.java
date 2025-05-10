package com.etrain.trainservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stations", schema = "etrain")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;

    private String stationCode;
}
