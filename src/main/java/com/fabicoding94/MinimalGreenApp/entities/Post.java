package com.fabicoding94.MinimalGreenApp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postName;
    @Lob       //it can be a large object
    private String description;
    private Integer likeCount = 0; //va incrementato con i like
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Instant createdDate;

}
