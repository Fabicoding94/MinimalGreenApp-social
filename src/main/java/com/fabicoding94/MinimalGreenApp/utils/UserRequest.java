package com.fabicoding94.MinimalGreenApp.utils;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRequest {

    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;

}