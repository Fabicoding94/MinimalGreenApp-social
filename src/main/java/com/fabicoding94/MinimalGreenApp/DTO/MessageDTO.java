package com.fabicoding94.MinimalGreenApp.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDTO {

    private String text;
    private Long senderId;
    private Long receiverId;

}
