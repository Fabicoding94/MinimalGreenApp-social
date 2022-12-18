package com.fabicoding94.MinimalGreenApp.services;

import com.fabicoding94.MinimalGreenApp.entities.post.Comment;
import com.fabicoding94.MinimalGreenApp.entities.user.User;
import com.fabicoding94.MinimalGreenApp.exceptions.NotFoundException;
import com.fabicoding94.MinimalGreenApp.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public Comment save(Comment x) {
        return repository.save(x);
    }

    public List<Comment> getAll() {
        return repository.findAll();
    }

    public Comment getById(Long id) {

        Optional<Comment> comment = repository.findById(id);

        if(comment.isEmpty())
            throw new NotFoundException("Comment Doesn't exist");

        return comment.get();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // ---------------------------- Paging --------------------------------

    public Page<Comment> getAllAndPaginate(Pageable p){
        Page<Comment> pe = repository.findAll(p);
        return pe;
    }

//	--------------------------- Filtering --------------------------------

//    public Page<Comment> getByPostAndPaginate(Long id, Pageable p){
//        return repository.getCommentsByPostId(id, p);
//    }

    /*public Page<Comment> getBySenderAndPaginate(Long id, Pageable p){
        return repository.getCommentsBySenderId(id, p);
    }*/

    public List<User> getCommentLikers(Long id){
        return repository.getCommentLikers(id);
    }

}
