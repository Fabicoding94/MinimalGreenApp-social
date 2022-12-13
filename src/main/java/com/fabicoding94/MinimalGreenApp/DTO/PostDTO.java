package com.fabicoding94.MinimalGreenApp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private String title;
    private String text;
    private Long authorId;

}
