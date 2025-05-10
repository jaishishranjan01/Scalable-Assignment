package com.etrain.trainservice.repository;

import com.etrain.trainservice.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {


    @Query("SELECT t FROM Train t WHERE t.sourceStation.id = :sourceId AND t.destinationStation.id = :destinationId")
    List<Train> searchTrain(@Param("sourceId") Long sourceStationId, @Param("destinationId") Long destinationStationId);
}
