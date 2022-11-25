package com.fabicoding94.MinimalGreenApp.entities;

import lombok.*;

import javax.persistence.*;


import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "commenti")
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private Instant createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
