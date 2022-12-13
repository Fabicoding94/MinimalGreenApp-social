package com.fabicoding94.MinimalGreenApp.entities.post;

import com.fabicoding94.MinimalGreenApp.entities.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private String title;
    private String text;
    private LocalDateTime date;
    private Boolean edited;


    @OneToMany(mappedBy="post", cascade=CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments;

    @ManyToMany
    @JoinTable(name = "posts_likers",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "liker_id")
    )
    private Set<User> likes;


    public void addLike(User user){
        this.likes.add(user);
    }


    public void removeLike(User user){
        this.likes.remove(user);
    }



}
