package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.post.Comment;
import com.fabicoding94.MinimalGreenApp.entities.post.Post;
import com.fabicoding94.MinimalGreenApp.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findByPost(Post post);

    //List<Comment> findAllByUser(User sender);

//    @Query( value = "SELECT c FROM Comment c WHERE c.post.id = :id" )
//    Page<Comment> getCommentsByPostId(Long id, Pageable p);

    @Query( value = "SELECT c FROM Comment c WHERE c.post.id = :id" )
    List<Comment> getCommentsByPostId();

    @Query( value = "SELECT c FROM Comment c WHERE c.sender.id = :id" )
    Page<Comment> getCommentsBySenderId(Long id, Pageable p);

    @Query( value = "SELECT c.likes FROM Comment c WHERE c.id = :id" )
    List<User> getCommentLikers(Long id);
}
