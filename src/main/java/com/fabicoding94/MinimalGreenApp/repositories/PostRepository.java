package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.Post;
import com.fabicoding94.MinimalGreenApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    // RITORNA UNA LISTA DI POST FILTRATE PER USER ID(PK)
    @Query(
            value = "select p from Post p where p.user.id = :id"
    )
    Page<Post> findPostByUserId(@Param("id") Long id, Pageable pageable );
}
