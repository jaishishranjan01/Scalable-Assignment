package com.etrain.trainservice.dto;

import lombok.Data;

public class TrainSearchRequest {
    private Long sourceStation;
    private Long destinationStation;

    public Long getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(Long sourceStation) {
        this.sourceStation = sourceStation;
    }

    public Long getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Long destinationStation) {
        this.destinationStation = destinationStation;
    }
}
