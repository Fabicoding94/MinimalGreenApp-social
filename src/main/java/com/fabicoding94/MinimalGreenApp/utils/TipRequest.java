package com.fabicoding94.MinimalGreenApp.utils;

import com.fabicoding94.MinimalGreenApp.entities.tip.TipType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TipRequest {

    private String tipTitle;
    private String tipContent;
    private TipType tipType;
}