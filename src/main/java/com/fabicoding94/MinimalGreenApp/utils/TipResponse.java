package com.fabicoding94.MinimalGreenApp.utils;

import com.fabicoding94.MinimalGreenApp.entities.Tip;
import com.fabicoding94.MinimalGreenApp.entities.TipType;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TipResponse {
    private Long id;
    private String tipTitle;
    private String tipContent;
    private TipType tipType;

    public static TipResponse parseUser(Tip tip ) {

        return TipResponse.builder()
                .id( tip.getId() )
                .tipTitle( tip.getTipTitle() )
                .tipContent( tip.getTipContent() )
                .tipType( tip.getTipType() )
                .build();
    }
}
