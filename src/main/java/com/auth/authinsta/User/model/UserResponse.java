package com.auth.authinsta.User.model;

import com.auth.authinsta.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName ;
    private String email;
    private Role role;
    private String password;
}
