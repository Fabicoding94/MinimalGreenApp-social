package com.fabicoding94.MinimalGreenApp.entities.user;

import com.fabicoding94.MinimalGreenApp.entities.post.Comment;
import com.fabicoding94.MinimalGreenApp.entities.post.Image;
import com.fabicoding94.MinimalGreenApp.entities.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    // Credentials
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private Boolean active;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public void addRole(Role r) {
        this.roles.add(r);
    }

    public void removeRole(Role r) {
        this.roles.remove(r);
    }

//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL) //meglio di no!!!
//   @JsonBackReference
//    private List<Post> posts ;

//    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)//meglio di no!!!
//    private List<Comment> comments ;

    @OneToMany(cascade= CascadeType.REMOVE, orphanRemoval = true, mappedBy = "sender")
    @JsonManagedReference
    private Set<Comment> comments;



    //info profile
    private String name;
    private String lastname;
    private String description;
    private Boolean isOnline;
    private Boolean isPrivate;
    private LocalDateTime registration;

    @OneToOne(cascade= CascadeType.ALL)

    private Image image;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "users_followed",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<User> followed;




    public void follow(User user) {
        this.followed.add(user);
    }

    public void unfollow(User user) {
        this.followed.remove(user);
    }



}
