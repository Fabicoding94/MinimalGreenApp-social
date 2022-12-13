package com.fabicoding94.MinimalGreenApp.entities.user;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    //TODO perchè mi salva localdate e non localdatetime? magari aggiungiamo ricerca messaggi in un lasso di tempo
    private LocalDateTime date;

    private Boolean edited;
}
