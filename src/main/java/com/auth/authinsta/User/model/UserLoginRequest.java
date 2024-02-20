package com.auth.authinsta.User.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    @Email(message = "Email is invalid")
    private String email;

    @NotEmpty(message = "Password can't be empty")
    private String password;
}
