package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.BTipElectricity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface BTipElectricityRepository extends JpaRepository<BTipElectricity, Long> {
    @Query(
            value = "select t from BTipElectricity t where t.tipTitle = :tipTitle"
    )
    Optional<BTipElectricity> findByTitle(@PathVariable("tipTitle") String tipTitle);
}
