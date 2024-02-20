package com.auth.authinsta.User.model;

import com.auth.authinsta.auth.model.Role;

public interface AuthorizedUserInfoProjection {
    public Role getRole();
    public Integer getId();
}
