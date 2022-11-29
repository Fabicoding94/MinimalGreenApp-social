package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.Tip;
import com.fabicoding94.MinimalGreenApp.entities.TipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {
    @Query(value = "select t from Tip t where t.tipTitle = :tipTitle")
    Optional<Tip> findByTitle(@PathVariable("tipTitle") String tipTitle);

    // RITORNA UNA LISTA DI TIPS FILTRATE PER TYPE
    @Query("select t from Tip t where t.tipType = :tipType")
    Page<Tip> findTipsByTipType(@Param( "tipType" ) TipType tipType, Pageable pageable );
}
