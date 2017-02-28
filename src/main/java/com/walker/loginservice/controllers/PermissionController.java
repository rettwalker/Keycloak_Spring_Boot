package com.walker.loginservice.controllers;


import com.walker.loginservice.domain.Permission;
import com.walker.loginservice.exceptions.PermissionNotFoundException;
import com.walker.loginservice.responses.AllPermissionsResponse;
import com.walker.loginservice.responses.PermissionResponse;
import com.walker.loginservice.services.PermissionService;
import com.walker.loginservice.util.Constants;
import com.walker.loginservice.util.SwaggerConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jmw0705 on 2/15/17.
 */
@RestController
public class PermissionController {

    private PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService){
        this.permissionService = permissionService;
    }

    @ApiOperation(value = SwaggerConstants.GET_ALL_PERMISSIONS, httpMethod = SwaggerConstants.GET)
    @RequestMapping(value = Constants.PATH_GET_ALL_PERMISSIONS, method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.SUCCESS, response = AllPermissionsResponse.class) })
    public ResponseEntity<AllPermissionsResponse> findAllPermissions(){
        AllPermissionsResponse allPermissionsResponse = new AllPermissionsResponse();
        try {
            allPermissionsResponse.setPermissions(permissionService.findAllPermissions());
            allPermissionsResponse.setMessage(Constants.SUCCESS);
            allPermissionsResponse.setResponseCode(HttpStatus.OK.value());
        } catch (Exception e){
            allPermissionsResponse.setErrorState(true);
            allPermissionsResponse.setMessage(Constants.UNEXPECTED_ERROR);
            allPermissionsResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new ResponseEntity<AllPermissionsResponse>(allPermissionsResponse, HttpStatus.valueOf(allPermissionsResponse.getResponseCode()));
    }

    @ApiOperation(value = SwaggerConstants.GET_PERMISSION, httpMethod = SwaggerConstants.GET)
    @RequestMapping(value = Constants.PATH_GET_PERMISSION, method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.SUCCESS, response = PermissionResponse.class) })
    public ResponseEntity<PermissionResponse> getRoleById(@PathVariable Long id) {
        PermissionResponse permissionResponse = new PermissionResponse();
        Permission permission = null;
        try {
            permission = permissionService.findPermissionById(id);
            permissionResponse.setPermission(permission);
            permissionResponse.setMessage(Constants.SUCCESS);
            permissionResponse.setResponseCode(HttpStatus.OK.value());
        } catch (PermissionNotFoundException e){
            permissionResponse.setMessage(e.getMessage());
            permissionResponse.setResponseCode(e.getResponseCode());
            permissionResponse.setErrorState(true);
        } catch (Exception e){
            permissionResponse.setMessage(Constants.UNEXPECTED_ERROR);
            permissionResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            permissionResponse.setErrorState(true);
        }
        return new ResponseEntity<PermissionResponse>(permissionResponse, HttpStatus.valueOf(permissionResponse.getResponseCode()));
    }
}
