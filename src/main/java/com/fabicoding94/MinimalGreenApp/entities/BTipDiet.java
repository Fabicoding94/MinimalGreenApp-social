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
@Table(name = "btips_diet")
public class BTipDiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipId;

    private String tipTitle;
    private String tipContent;

    @ManyToOne
    @JoinColumn(name = "box_sustainable_diet_id")
    private BoxSustainableDiet boxSustainableDiet;

}
