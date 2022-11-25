package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.BTipWater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface BTipWaterRepository extends JpaRepository<BTipWater, Long> {
    @Query(
            value = "select t from BTipWater t where t.tipTitle = :tipTitle"
    )
    Optional<BTipWater> findByTitle( @PathVariable("tipTitle") String tipTitle);
}
