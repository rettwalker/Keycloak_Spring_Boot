package com.walker.loginservice.util;

/**
 * Created by rettwalker on 2/10/17.
 */
public class SwaggerConstants {

    public static final String ADD_NEW_ROLE = "/RoleServices/roles";
    public static final String GET_ALL_ROLES = "/RoleServices/roles";
    public static final String DELETE_ROLE = "/RoleServices/roles/{id}";
    public static final String GET_ROLE_BY_ID = "/RoleServices/roles/{id}";
    public static final String ADD_PERMISSION_TO_ROLE = "/RoleServices/roles/{id}/permissions";
    public static final String REMOVE_PERMISSION_FROM_ROLE = "/RoleServices/roles/{id}/permissions/{permissionId}";

    public static final String GET_ALL_PERMISSIONS = "/PermissionServices/permissions";
    public static final String GET_PERMISSION = "/PermissionServices/permissions/{id}";




    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";
}
