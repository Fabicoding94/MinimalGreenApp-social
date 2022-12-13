package com.fabicoding94.MinimalGreenApp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String description;
    private Boolean isPrivate;

}
