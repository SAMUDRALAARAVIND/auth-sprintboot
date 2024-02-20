package com.auth.authinsta.User.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchRequest {
    private Integer id;
    private String firstName;
    private String email;
    private String phoneNumber;

    @NotNull(message = "page number is required")
    private Integer pageNumber;
    @NotNull(message = "page size is required")
    private Integer pageSize;
}
