package com.fabicoding94.MinimalGreenApp.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private final String type = "Bearer";

    private int id;
    private String username;
    private String email;
    private List<String> roles;
    private Date expirationTime;

}
