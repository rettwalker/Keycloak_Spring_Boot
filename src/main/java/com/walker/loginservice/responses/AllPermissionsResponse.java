package com.walker.loginservice.responses;


import com.walker.loginservice.domain.Permission;

import java.util.List;

/**
 * Created by jmw0705 on 2/15/17.
 */
public class AllPermissionsResponse extends ResponseCore {
    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
