package com.auth.authinsta.User.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IamResponse {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IamUserInfo {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String password;
        private String profileImageUrl;
    }

    private Boolean isActiveUser;
    private Long feeDeadlineDate;
    private IamUserInfo userInfo;
}
