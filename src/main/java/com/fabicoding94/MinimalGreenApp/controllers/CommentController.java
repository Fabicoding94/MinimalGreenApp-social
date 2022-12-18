package com.fabicoding94.MinimalGreenApp.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabicoding94.MinimalGreenApp.services.*;
import com.fabicoding94.MinimalGreenApp.entities.post.*;
import com.fabicoding94.MinimalGreenApp.entities.user.*;
import com.fabicoding94.MinimalGreenApp.DTO.*;





@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200/")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

//---------------------------- Get ---------------------------------

//    @GetMapping
//    public ResponseEntity<Page<Comment>> getCommentList(Pageable p) {
//
//        Page<Comment> res = commentService.getAllAndPaginate(p);
//
//        if (res.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else{
//            return new ResponseEntity<>(res, HttpStatus.OK);
//        }
//    }

    @GetMapping
    public List<Comment> getCommentList() {

        return commentService.getAll();

    }

    @GetMapping("{id}")
    public Comment getCommentById(@PathVariable("id") Long id) {
        return commentService.getById(id);
    }

//    @GetMapping("post_id/{id}")
//    public Page<Comment> getCommentsByPost(@PathVariable("id") Long id, Pageable p) {
//        return commentService.getByPostAndPaginate(id, p);
//    }

//    @GetMapping("sender_id/{id}")
//    public Page<Comment> getCommentsBySender(@PathVariable("id") Long id, Pageable p) {
//        return commentService.getBySenderAndPaginate(id, p);
//    }

    @GetMapping("{id}/likers")
    public List<User> getCommentLikers(@PathVariable("id") Long id){
        return commentService.getCommentLikers(id);
    }

//---------------------------- Post --------------------------------

    @PostMapping
    public Comment saveComment( @RequestBody CommentDTO dto ) throws Exception {

        Comment comment = Comment.builder()
                .text(dto.getText())
                .sender(userService.getById(dto.getSenderId()))
                .post(postService.getById(dto.getPostId()))
                .likes(new HashSet<User>())
                .date(LocalDateTime.now())
                .edited(false)
                .build();

        return commentService.save(comment);
    }

    //---------------------------- Put ---------------------------------

//    @PutMapping("/{id}")
//    public Comment updateComment(@PathVariable("id") Long id, @RequestBody String text ) {
//
//        Comment comment = commentService.getById(id);
//        comment.setText(text);
//        comment.setEdited(true);
//
//        return commentService.save(comment);
//    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody CommentUpDateDTO update) {

        Comment comment = commentService.getById(id);

        if (update.getText() != null) comment.setText(update.getText());

        commentService.save(comment);
        return comment;
    }

    @PutMapping("/{id}/like")
    public Comment like(@PathVariable("id") Long likedId, @RequestBody Long likerId){

        Comment liked = commentService.getById(likedId);
        User liker = userService.getById(likerId);

        if(liked.getLikes().contains(liker))
            liked.removeLike(liker);
        else
            liked.addLike(liker);

        commentService.save(liked);

        return liked;

    }

    // -------------------------- Delete -------------------------------

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCommentById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return "comment deleted successfully";
    }

}
