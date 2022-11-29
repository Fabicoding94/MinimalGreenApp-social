package com.fabicoding94.MinimalGreenApp.entities;


import lombok.*;

import javax.persistence.*;

//Un tip è un post statico, senza commenti
//infatti ha solo scopo informativo
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tips")
public class Tip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String tipTitle;
    private String tipContent;
    private TipType tipType;


}
