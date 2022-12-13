package com.fabicoding94.MinimalGreenApp.utils;

import com.fabicoding94.MinimalGreenApp.entities.user.Role;
import com.fabicoding94.MinimalGreenApp.entities.user.User;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserResponse {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private Set<Role> roles;

    public static UserResponse parseUser( User user ) {

        return UserResponse.builder()
                .id( user.getId() )
                .name( user.getName() )
                .lastname( user.getLastname() )
                .email( user.getEmail() )
                .username( user.getUsername() )
                .roles( user.getRoles() )
                .build();
    }
}
