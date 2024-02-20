package com.auth.authinsta.User.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchResponse {
    private Integer pageNumber;
    private Integer pageSize;
    private List<UserResponse> usersList;
    private Boolean hasNextPage;
}
