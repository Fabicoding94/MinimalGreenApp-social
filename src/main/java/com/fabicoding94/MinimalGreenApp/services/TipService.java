package com.fabicoding94.MinimalGreenApp.services;


import com.fabicoding94.MinimalGreenApp.entities.Tip;
import com.fabicoding94.MinimalGreenApp.entities.TipType;

import com.fabicoding94.MinimalGreenApp.entities.User;
import com.fabicoding94.MinimalGreenApp.repositories.TipRepository;
import com.fabicoding94.MinimalGreenApp.utils.TipRequest;
import com.fabicoding94.MinimalGreenApp.utils.TipResponse;
import com.fabicoding94.MinimalGreenApp.utils.UserRequest;
import com.fabicoding94.MinimalGreenApp.utils.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class TipService {
    @Autowired
    TipRepository tipRepository;


    // GET BY ID
    public Tip getById( Long id ) throws Exception {
        Optional<Tip> tip = tipRepository.findById( id );
        if( tip.isEmpty() )
            throw new Exception( "Tip not available" );
        return tip.get();
    }

    // GET ALL AND PAGINATE
    public Page<Tip> getAllPaginate(Pageable p ) {
        return tipRepository.findAll( p );
    }


    // CREATE
    public void save( Tip tip ) {
        tipRepository.save( tip );
    }

    ;

//    // UPDATE
//    public void update( Tip t ) {
//        tipRepository.save( t );
//    }


    // UPDATE AND SAVE
    public TipResponse updateResponse(TipRequest tipRequest, Long id ) {
        Optional<Tip> tipFind = tipRepository.findById( id );

        if( tipFind.isPresent() ) {
            Tip t = new Tip();
            t.setId( tipFind.get().getId() );
            t.setTipTitle( tipRequest.getTipTitle() == null ? tipFind.get().getTipTitle()
                    : tipRequest.getTipTitle() );
            t.setTipContent( tipRequest.getTipContent() == null ? tipFind.get().getTipContent()
                    : tipRequest.getTipContent() );
            t.setTipType( tipRequest.getTipType() == null ? tipFind.get().getTipType()
                    : tipRequest.getTipType() );


            tipRepository.save( t );
            return TipResponse.parseUser( tipFind.get() );
        } else {
            return null;
        }

    }

    // UPDATE
    public void update( Tip t ) {
        tipRepository.save( t );
    }

    // DELETE
    public void delete( Long id ) throws Exception {
        Optional<Tip> tip = tipRepository.findById( id );
        if( tip.isPresent() ) {
            tipRepository.delete( tip.get() );
        } else {
            throw new Exception( "Tip non trovata" );
        }
    }


    /////////////////////////////////////////////////////////////
    ///////////////////////// FILTER BY /////////////////////////
    /////////////////////////////////////////////////////////////


    // RITORNA UNA LISTA DI Tips FILTRATE PER TipType
    public Page<Tip> filterTipByTipType( TipType tipType, Pageable pageable ) {
        return tipRepository.findTipsByTipType(tipType, pageable);
    }



}
