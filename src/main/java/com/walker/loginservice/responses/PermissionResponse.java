package com.walker.loginservice.responses;


import com.walker.loginservice.domain.Permission;

/**
 * Created by jmw0705 on 2/15/17.
 */
public class PermissionResponse extends ResponseCore {
    private Permission permission;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
