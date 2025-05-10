package com.etrain.trainservice.dto;

import lombok.Data;

@Data
public class TrainSearchRequest {
    private Long sourceStation;
    private Long destinationStation;
}
