package com.fabicoding94.MinimalGreenApp.services;

import com.fabicoding94.MinimalGreenApp.entities.user.User;
import com.fabicoding94.MinimalGreenApp.exceptions.NotFoundException;
import com.fabicoding94.MinimalGreenApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //-----------------------SAVE-------------------------
    public User save(User x) {
        return userRepository.save(x);
    }

    //-----------------------GET----------------------------
    // GET ALL
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // GET BY ID
    public User getById(Long id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new NotFoundException("User Doesn't exist");

        return user.get();
    }

    // GET BY USERNAME
    public Optional<User> findByUsername( String username ) {

        return userRepository.findByUsername( username );
    }



    // ---------------------------- Paging --------------------------------
    //GET AND PAGINATE
    public Page<User> getAllAndPaginate(Pageable p){
        Page<User> pe = userRepository.findAll(p);
        return pe;
    }

    public Page<User> getByNameAndPaginate(String n, Pageable p){
        return userRepository.findByNameAndPaginate(n, p);
    }

    // -------------------------- Filter ----------------------------

    public List<User> getAllFollowers(Long id){
        return userRepository.getFollowers(id);
    }

    public List<User> getAllFollowed(Long id){
        return userRepository.getFollowed(id);
    }
    //-----------------------DELETE-------------------------
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    //-----------------------UPDATE-------------------------
    // UPDATE AND SAVE
//    public UserResponse updateResponse(UserRequest userRequest, Long id ) {
//        Optional<User> userFind = userRepository.findById( id );
//
//        if( userFind.isPresent() ) {
//            User u = new User();
//            u.setId( userFind.get().getId() );
//            u.setCompleteName( userRequest.getNomeCompleto() == null ? userFind.get().getCompleteName()
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
//
//    }



    // UPDATE
    public void update( User u ) {
        userRepository.save( u );
    }




}
