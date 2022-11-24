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
@Table(name = "boxReduceCO2")
public class BoxReduceCO2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boxId;
    private String nameTipsBox;
    @OneToMany(mappedBy="boxReduceCO2")
    private List<BTipCO2> tipsCO2;
}
