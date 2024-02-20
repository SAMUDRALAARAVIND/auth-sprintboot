package com.auth.authinsta.auth;

import com.auth.authinsta.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {
    private Integer userId;
    private Role role;
}
