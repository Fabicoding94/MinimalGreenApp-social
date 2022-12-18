package com.fabicoding94.MinimalGreenApp.controllers;

import com.fabicoding94.MinimalGreenApp.DTO.PostDTO;
import com.fabicoding94.MinimalGreenApp.entities.post.Comment;
import com.fabicoding94.MinimalGreenApp.entities.post.Image;
import com.fabicoding94.MinimalGreenApp.entities.post.Post;
import com.fabicoding94.MinimalGreenApp.entities.user.User;
import com.fabicoding94.MinimalGreenApp.services.ImageService;
import com.fabicoding94.MinimalGreenApp.services.PostService;
import com.fabicoding94.MinimalGreenApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins= "http://localhost:4200")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;


//---------------------------- Get ---------------------------------

    @GetMapping("page")
    public ResponseEntity<Page<Post>> getPagedPostList(Pageable p) {

        Page<Post> res = postService.getAllAndPaginate(p);

        if (res.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping
    public List<Post> getPostList() {

        return postService.getAll();


    }

    @GetMapping("{id}")
    public Post getPostById(@PathVariable("id") Long id) throws Exception {
        return postService.getById(id);
    }

    @GetMapping("/author_name/{author}")
    public Page<Post> getPostById(@PathVariable("author") String author, Pageable p) {
        return postService.getByAuthorAndPaginate(author, p);
    }

    @GetMapping("{id}/likers")
    public List<User> getPostLikers(@PathVariable("id") Long id){
        return postService.getPostLikers(id);
    }

//---------------------------- Post --------------------------------

    @PostMapping(/*consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}*/)
    public Post savePost(@RequestBody PostDTO dto /*, @RequestPart("imageFile") MultipartFile file*/) {

      //  Image postImage = uploadImage(file);

      //  imageService.save(postImage);

        Post post = Post.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .author(userService.getById(dto.getAuthorId()))
                .date(LocalDateTime.now())
                .likes(new HashSet<User>())
                .comments(new HashSet<Comment>())
                .edited(false)
       //         .image(postImage)
                .build();

        return postService.save(post);
    }

    // TODO forse è meglio un exception handler
    public Image uploadImage(MultipartFile file) {

        try {
            Image img = Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imgBytes(file.getBytes())
                    .build();
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    //---------------------------- Put ---------------------------------

    @PutMapping("/{id}") //TODO un post in teoria ha anche un immagine
    public Post updatePost(@PathVariable("id") Long id, @RequestBody String text ) throws Exception {

        Post post = postService.getById(id);
        post.setText(text);
        post.setEdited(true);

        return postService.save(post);
    }

    @PutMapping("/{id}/like")
    public Post like(@PathVariable("id") Long likedId, @RequestBody Long likerId) throws Exception {

        Post liked = postService.getById(likedId);
        User liker = userService.getById(likerId);

        if(liked.getLikes().contains(liker))
            liked.removeLike(liker);
        else
            liked.addLike(liker);

        postService.save(liked);

        return liked;

    }

    // -------------------------- Delete -------------------------------

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserById(@PathVariable("id") Long id) {
        postService.deleteById(id);

    }



}
