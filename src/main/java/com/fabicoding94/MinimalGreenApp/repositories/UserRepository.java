package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    //ricerca per parziale dello username
//    @Query(
//            value = "select c from User c where upper(c.username) like upper(concat('%', :nome, "
//                    + "'%'))"
//    )
//    List<User> getUserByUsernameContains(@Param( "username" ) String username );

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM users WHERE lower(name) LIKE %:n%"
    )
    Page<User> findByNameAndPaginate(String n, Pageable p);

    @Query( value = "SELECT u FROM User u INNER JOIN u.followed i WHERE i.id = :id" )
    List<User> getFollowers(Long id);

    @Query( value = "SELECT u.followed FROM User u WHERE u.id = :id" )
    List<User> getFollowed(Long id);
}
