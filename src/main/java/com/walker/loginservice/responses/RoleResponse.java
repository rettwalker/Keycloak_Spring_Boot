package com.walker.loginservice.responses;


import com.walker.loginservice.domain.Role;

/**
 * Created by rettwalker on 2/11/17.
 */
public class RoleResponse extends ResponseCore {
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
