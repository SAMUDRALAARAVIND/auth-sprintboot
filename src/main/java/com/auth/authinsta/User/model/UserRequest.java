package com.auth.authinsta.User.model;


import com.auth.authinsta.auth.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @Email(message = "Invalid email id")
    private String email;

    @NotNull(message = "User role is required")
    private Role role;

    @NotNull(message = "first name is required")
    private String firstName ;

    private String lastName;

    @Pattern(regexp = "^(?=.{8,15})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$!]).*$", message = "Password must be 8 to 15 characters long and can contain only alpha numeric and special chars #@$!*&")
    @NotEmpty(message = "Password is required")
    private String password;
}
