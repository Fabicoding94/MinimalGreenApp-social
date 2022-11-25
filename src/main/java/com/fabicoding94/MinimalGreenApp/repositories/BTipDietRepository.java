package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.BTipCO2;
import com.fabicoding94.MinimalGreenApp.entities.BTipDiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface BTipDietRepository extends JpaRepository<BTipDiet, Long> {
    @Query(
            value = "select t from BTipDiet t where t.tipTitle = :tipTitle"
    )
    Optional<BTipDiet> findByTitle(@PathVariable("tipTitle") String tipTitle);
}
