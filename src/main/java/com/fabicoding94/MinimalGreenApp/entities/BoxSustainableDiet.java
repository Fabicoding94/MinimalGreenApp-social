package com.fabicoding94.MinimalGreenApp.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "boxSustainableDiet")
public class BoxSustainableDiet{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nameTipsBox;
    @OneToMany(mappedBy="boxSustainableDiet")
    private List<BTipDiet> tipsDiet;
}
