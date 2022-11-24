package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.BTipCO2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface BTipCO2Repository extends JpaRepository<BTipCO2, Long> {
    @Query(
            value = "select t from BTipCO2 t where t.tipTitle = :tipTitle"
    )
    Optional<BTipCO2> findByTitle( @PathVariable("tipTitle") String tipTitle);
}
