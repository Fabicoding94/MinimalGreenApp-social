package com.fabicoding94.MinimalGreenApp.services;

import com.fabicoding94.MinimalGreenApp.entities.post.Post;
import com.fabicoding94.MinimalGreenApp.entities.user.User;
import com.fabicoding94.MinimalGreenApp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository repository;

    // GET BY ID
    public Post getById(Long id) throws Exception {
        Optional<Post> post = repository.findById(id);
        if ( post.isEmpty() )
            throw new Exception("Post not available");
        return post.get();
    }



    // GET ALL PAGEABLE
    public Page<Post> getAllPaginate(Pageable p) {
        return repository.findAll(p);
    }

    // GET ALL
    public List<Post> getAll() {
        return repository.findAll();
    }

    // CREATE
    public Post save( Post x) {
        return repository.save(x);
    }

    // DELETE
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // ---------------------------- Paging --------------------------------

    public Page<Post> getAllAndPaginate(Pageable p){
        Page<Post> pe = repository.findAll(p);
        return pe;
    }

//	-------------------------- Filtering ---------------------------------

    public Page<Post> getByAuthorAndPaginate(String author, Pageable p){
        return repository.findByAuthor(author, p);
    }

    public List<Post> getByAuthorId(Long id){
        return repository.findByAuthorId(id);
    }

    public List<User> getPostLikers(Long id){
        return repository.getPostLikers(id);
    }



    public void deletePostList(List<Post> userPosts) {
        for(Post p : userPosts) {
            repository.deleteById(p.getPostId());
        }

    }




}



