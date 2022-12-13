package com.fabicoding94.MinimalGreenApp.entities.post;

import com.fabicoding94.MinimalGreenApp.entities.post.Post;
import com.fabicoding94.MinimalGreenApp.entities.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private LocalDateTime date;
    private Boolean edited;

    @ManyToOne
    @JsonBackReference
    private User sender;

    @ManyToOne
    @JsonBackReference
    private Post post;

    @ManyToMany
    @JoinTable(
            name = "comments_likers",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "liker_id")
    )
    private Set<User> likes;

    public void addLike(User user) {
        this.likes.add(user);
    }

    public void removeLike(User user) {
        this.likes.remove(user);
    }



}
