package com.etrain.trainservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets", schema = "etrain")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnrNumber;           // Unique PNR Number
    private Long trainId;               // Optional redundancy (useful for fast queries)
    private String trainName;

    @ManyToOne
    @JoinColumn(name = "source_station_id")
    private Station sourceStation;

    @ManyToOne
    @JoinColumn(name = "destination_station_id")
    private Station destinationStation;

    private LocalDate journeyDate;

    private String transactionId;        // Payment transaction ID
    private String bookingStatus;        // Confirmed / Failed

    private Integer numberOfSeats;       // Number of seats booked
    private Double totalFare;            // Total Fare collected

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers = new ArrayList<>();

}
