package com.fabicoding94.MinimalGreenApp.services;


import com.fabicoding94.MinimalGreenApp.entities.User;
import com.fabicoding94.MinimalGreenApp.repositories.UserRepository;
import com.fabicoding94.MinimalGreenApp.utils.UserRequest;
import com.fabicoding94.MinimalGreenApp.utils.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    // GET BY ID
    public User getById( Long id ) throws Exception {
        Optional<User> user = userRepository.findById( id );
        if( user.isEmpty() )
            throw new Exception( "User not available" );
        return user.get();
    }

    // GET BY USERNAME
    public Optional<User> findByUsername( String username ) {

        return userRepository.findByUsername( username );
    }

//    // GET BY USERNAME CONTAINS
//    public List<User> findByUsername( String username) {
//        return userRepository.getUserByUsername( username );
//    }

    // GET ALL
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // GET ALL PAGEABLE
    public Page<User> getAllPaginate( Pageable p ) {
        return userRepository.findAll( p );
    }

    // CREATE
    public User save( User u ) {
        String psw = u.getPassword();
        u.setPassword( encoder.encode( psw ) );
        return userRepository.save( u );
    }


    // UPDATE AND SAVE
    public UserResponse updateResponse(UserRequest userRequest, Long id ) {
        Optional<User> userFind = userRepository.findById( id );

        if( userFind.isPresent() ) {
            User u = new User();
            u.setId( userFind.get().getId() );
            u.setCompleteName( userRequest.getNomeCompleto() == null ? userFind.get().getCompleteName()
                    : userRequest.getNomeCompleto() );
            u.setEmail( userRequest.getEmail() == null ? userFind.get().getEmail() : userRequest.getEmail() );
            u.setUsername( userRequest.getUsername() == null ? userFind.get().getUsername() :
                    userRequest.getUsername() );
            u.setPassword( userFind.get().getPassword() );
            u.setRoles( userFind.get().getRoles() );
            u.setActive( userFind.get().getActive() );

            userRepository.save( u );
            return UserResponse.parseUser( userFind.get() );
        } else {
            return null;
        }


    }



    // UPDATE
    public void update( User u ) {
        userRepository.save( u );
    }

//    // UPDATE AND SAVE
//    public UserResponse updateResponse( UserRequest userRequest, Long id ) {
//        Optional<User> userFind = userRepository.findById( id );
//
//        if( userFind.isPresent() ) {
//            User u = new User();
//            u.setId( userFind.get().getId() );
//            u.setNomeCompleto( userRequest.getNomeCompleto() == null ? userFind.get().getNomeCompleto()
//                    : userRequest.getNomeCompleto() );
//            u.setEmail( userRequest.getEmail() == null ? userFind.get().getEmail() : userRequest.getEmail() );
//            u.setUsername( userRequest.getUsername() == null ? userFind.get().getUsername() :
//                    userRequest.getUsername() );
//            u.setPassword( userFind.get().getPassword() );
//            u.setRoles( userFind.get().getRoles() );
//            u.setActive( userFind.get().getActive() );
//
//            userRepository.save( u );
//            return UserResponse.parseUser( userFind.get() );
//        } else {
//            return null;
//        }
//
//    }

    // DELETE
    public void delete( Long id ) throws Exception {
        Optional<User> u = userRepository.findById( id );
        if( u.isPresent() ) {
            userRepository.delete( u.get() );
        } else {
            throw new Exception( "Utente non trovato" );
        }
    }

}
