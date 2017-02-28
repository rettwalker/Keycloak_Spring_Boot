package com.walker.loginservice.responses;

import com.walker.loginservice.domain.Role;

import java.util.List;

/**
 * Created by jmw0705 on 2/13/17.
 */
public class AllRolesResponse extends ResponseCore {
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
