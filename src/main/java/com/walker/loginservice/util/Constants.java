package com.walker.loginservice.util;

/**
 * Created by jmw0705 on 2/10/17.
 */
public class Constants {

    /*ROLES*/

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_LEADER = "LEADER";

    /*PERMISSIONS*/


    public static final String PERM_ADMIN_PROFILE = "ADMIN_PROFILE";
    public static final String PERM_USER_MANAGEMENT = "USER_MANAGEMENT";

    /*ERROR MESSAGES*/

    public static final String ROLE_ALREADY_EXISTS = "Role Already Exists";
    public static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    public static final String PERMISSION_NOT_FOUND = "Permission Not Found";


    /*RESPONSE MESSAGES*/
    public static final String SUCCESS = "SUCCESS";

    /*ENDPOINT PATHS*/
    public static final String PATH_CREATE_NEW_ROLE = "/RolesServices/roles";
    public static final String PATH_GET_ALL_ROLES = "/RoleServices/roles";
    public static final String PATH_DELETE_ROLE = "/RoleServices/roles/{id}";
    public static final String PATH_GET_ROLE_BY_ID = "/RoleServices/roles/{id}";
    public static final String PATH_ADD_PERMISSION_TO_ROLE = "/RoleServices/roles/{id}/permissions";
    public static final String PATH_REMOVE_PERMISSION_FROM_ROLE = "/RoleServices/roles/{id}/permissions/{permissionId}";

    public static final String PATH_GET_ALL_PERMISSIONS = "/PermissionServices/permissions";
    public static final String PATH_GET_PERMISSION = "/PermissionServices/permissions/{id}";


}
