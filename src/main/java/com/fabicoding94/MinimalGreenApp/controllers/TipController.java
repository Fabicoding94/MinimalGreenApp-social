package com.fabicoding94.MinimalGreenApp.controllers;


import com.fabicoding94.MinimalGreenApp.entities.tip.Tip;
import com.fabicoding94.MinimalGreenApp.entities.tip.TipType;
import com.fabicoding94.MinimalGreenApp.repositories.TipRepository;
import com.fabicoding94.MinimalGreenApp.services.TipService;
import com.fabicoding94.MinimalGreenApp.utils.TipRequest;
import com.fabicoding94.MinimalGreenApp.utils.TipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tips")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class TipController {

    @Autowired
    private TipService tipService;

    @Autowired
    private TipRepository tipRepository ;


    // GET ALL the tips - (TUTTI gli ospiti possono leggere le tips senza login)
    @GetMapping("")
    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Tip>> getAll() {
        return new ResponseEntity<>(tipRepository.findAll(), HttpStatus.OK);
    }

    // GET ALL the tips AND PAGINATE
    @GetMapping("/pageable")
    //@PreAuthorize("hasAnyRole('ADMIN','USER')") - (TUTTI gli ospiti possono leggere le tips)
    public ResponseEntity<Page<Tip>> getAllPageable( Pageable p) {
        return new ResponseEntity<>(tipService.getAllPaginate( p ), HttpStatus.OK);
    }

    // GET tip BY ID
    @GetMapping("/{}")
    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Tip> get(@PathVariable("id") Long id ) throws Exception {
        return new ResponseEntity<>(
                tipService.getById( id ),
                HttpStatus.OK
        );
    }

    //GET BY TipTITLE
    @GetMapping("/tipTitle/{tipTitle}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tip> getByTipTitle( @PathVariable String tipTitle ) {

        return new ResponseEntity<>(
                tipService.findByTitle( tipTitle ).isPresent() ?
                        tipService.findByTitle( tipTitle ).get() : null,
                HttpStatus.OK
        );

    }

    // GET BY TipTITLE CONTAINS
    @GetMapping("/tipTitle-contains/{tipTitle}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Tip>> getByTipTitleContains( @PathVariable String tipTitle ) {

        return new ResponseEntity<>(
                tipService.findByTipTitleContains( tipTitle ),
                HttpStatus.OK
        );

    }

    // CREATE NEW TIPS - Solo i MODERATORS possono postare le Tips
    @PostMapping("/new-tip")
    //@PreAuthorize("hasAnyRole('MODERATOR')")
    public ResponseEntity<Tip> create( @RequestBody TipRequest tipRequest ) {
        try {
            Tip tip = Tip.builder()
                    .tipTitle(tipRequest.getTipTitle())
                    .tipContent(tipRequest.getTipContent())
                    .tipType( TipType.valueOf(String.valueOf(tipRequest.getTipType())))
                    .build();
            tipService.save( tip );
            return new ResponseEntity<>( tip, HttpStatus.OK ) ;
        } catch( Exception e ) {
            log.error( e.getMessage() );
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // UPDATE - Solo i MODERATORS possono modificare le Tips
    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<TipResponse> update(@RequestBody TipRequest tip, @PathVariable("id") Long id ) {
        try {
            return new ResponseEntity<>( tipService.updateResponse( tip, id ),
                    HttpStatus.OK);
        } catch( Exception e ) {
            log.error( e.getMessage() );
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }



    //DELETE - Solo i MODERATORS possono cancellare le Tips
    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasAnyRole('MODERATOR')")
    public void deleteById( @PathVariable("id") Long id ) {
        try {
            tipService.delete( id );
        } catch( Exception e ) {
            log.error( e.getMessage() );
        }
    }


    // RITORNA UNA LISTA DI Tips FILTRATE PER TipType
    @GetMapping("/tipType/{tipType}")
    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Tip>> getTipsByTipType( @PathVariable("tipType") TipType tipType ) {

        return new ResponseEntity<>(
                tipService.filterTipByTipType( tipType  ),
                HttpStatus.OK
        );
    }

    @GetMapping("/tipTypeP/{tipType}")
    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Tip>> getTipsByTipTypeP( @PathVariable("tipType") TipType tipType ) {

        return new ResponseEntity<>(
                tipService.filterTipByTipType( tipType  ),
                HttpStatus.OK
        );
    }

}
