package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.post.Post;
import com.fabicoding94.MinimalGreenApp.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //List<Post> findByUser(User user);

    // RITORNA UNA LISTA DI POST FILTRATE PER USER ID(PK)
    @Query(
            value = "select p from Post p where p.author.id = :id"
    )
    Page<Post> findPostByUserId(@Param("id") Long id, Pageable pageable );

    @Query(value = "SELECT p FROM Post p WHERE p.author.id = :id")
    List<Post> findByAuthorId(Long id);

    @Query(value = "SELECT p FROM Post p WHERE p.author.username LIKE %:author%")
    Page<Post> findByAuthor(String author, Pageable p);

    @Query(value = "SELECT p.likes FROM Post p WHERE p.id = :id")
    List<User> getPostLikers(Long id);

    @Modifying
    @Query(value = "DELETE FROM Post p WHERE p.author.id = :id")
    void deleteByAuthorId(Long id);
}
