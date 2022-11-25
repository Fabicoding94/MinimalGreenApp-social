package com.fabicoding94.MinimalGreenApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String completeName;
    @Column(unique= true)
    private String username;
    private String password;
    @Column(unique= true)
    private String email;
    private Boolean active = true;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();

    public void addRole( Role r ) {

        this.roles.add( r );

    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();


}
