package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.tip.Tip;
import com.fabicoding94.MinimalGreenApp.entities.tip.TipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {
//    @Query(value = "select t from Tip t where t.tipTitle = :tipTitle")
//    Optional<Tip> findByTitle(@PathVariable("tipTitle") String tipTitle);

    Optional<Tip> findByTipTitle(String tipTitle);

    // RITORNA UNA LISTA DI TIPS FILTRATE PER TYPE
    @Query("select t from Tip t where t.tipType = :tipType")
    List<Tip> findTipsByTipType(@Param( "tipType" ) TipType tipType );

    // RITORNA UNA LISTA DI TIPS che hanno in comune i caratteri digitati
    @Query(
            value = "select t from Tip t where upper(t.tipTitle) like upper(concat('%', :tipTitle, "
                    + "'%'))"
    )
    List<Tip> getTipByTipTitleContains(@Param( "tipTitle" ) String tipTitle );
}
