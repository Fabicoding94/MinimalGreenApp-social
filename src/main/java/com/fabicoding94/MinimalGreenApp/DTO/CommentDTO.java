package com.fabicoding94.MinimalGreenApp.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDTO {

    private String text;
    private Long senderId;
    private Long postId;

}
