package com.fabicoding94.MinimalGreenApp.entities;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "boxElectricity")
public class BoxSaveElectricity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boxId;
    private String nameTipsBox;
    @OneToMany(mappedBy="boxSaveElectricity")
    private List<BTipElectricity> tipsElectricity;
}
