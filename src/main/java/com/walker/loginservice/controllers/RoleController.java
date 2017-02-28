package com.walker.loginservice.controllers;


import com.walker.loginservice.domain.Permission;
import com.walker.loginservice.domain.Role;
import com.walker.loginservice.exceptions.RoleAlreadyExistsException;
import com.walker.loginservice.exceptions.RoleNotFoundException;
import com.walker.loginservice.responses.AllRolesResponse;
import com.walker.loginservice.responses.ResponseCore;
import com.walker.loginservice.responses.RoleResponse;
import com.walker.loginservice.services.RoleService;
import com.walker.loginservice.util.Constants;
import com.walker.loginservice.util.SwaggerConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rettwalker on 2/10/17.
 */
@RestController
public class RoleController {

    private final Logger log = LoggerFactory.getLogger(RoleController.class);

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }


    @ApiOperation(value = SwaggerConstants.ADD_NEW_ROLE, httpMethod = SwaggerConstants.POST)
    @RequestMapping(value = Constants.PATH_CREATE_NEW_ROLE, method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.SUCCESS, response = RoleResponse.class) })
    public ResponseEntity<RoleResponse> addNewRole(@RequestBody Role role){
        log.info("Creating Role", role);
        RoleResponse roleResponse = new RoleResponse();
        try {
            role = roleService.addNewRole(role);
            roleResponse.setResponseCode(HttpStatus.CREATED.value());
            roleResponse.setMessage(Constants.SUCCESS);
            roleResponse.setRole(role);
        } catch (RoleAlreadyExistsException e) {
            roleResponse.setMessage(Constants.ROLE_ALREADY_EXISTS);
            roleResponse.setErrorState(true);
            roleResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (Exception e){
            log.info("Error", e.getLocalizedMessage());
            roleResponse.setMessage(Constants.UNEXPECTED_ERROR);
            roleResponse.setErrorState(true);
            roleResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ResponseEntity<RoleResponse>(roleResponse, HttpStatus.valueOf(roleResponse.getResponseCode()));
    }

    @ApiOperation(value = SwaggerConstants.GET_ALL_ROLES, httpMethod = SwaggerConstants.GET)
    @RequestMapping(value = Constants.PATH_GET_ALL_ROLES, method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.SUCCESS, response = AllRolesResponse.class) })
    public ResponseEntity<AllRolesResponse> findAllRoles(){
        AllRolesResponse allRolesResponse = new AllRolesResponse();

        try {
            allRolesResponse.setRoles(roleService.findAll());
            allRolesResponse.setMessage(Constants.SUCCESS);
            allRolesResponse.setResponseCode(HttpStatus.OK.value());
        } catch (Exception e){
            log.info("Error", e.getLocalizedMessage());
            allRolesResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            allRolesResponse.setMessage(Constants.UNEXPECTED_ERROR);
            allRolesResponse.setErrorState(true);
        }

        return new ResponseEntity<AllRolesResponse>(allRolesResponse, HttpStatus.valueOf(allRolesResponse.getResponseCode()));
    }

    @ApiOperation(value = SwaggerConstants.DELETE_ROLE, httpMethod = SwaggerConstants.DELETE)
    @RequestMapping(value = Constants.PATH_DELETE_ROLE, method = RequestMethod.DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.SUCCESS, response = ResponseCore.class) })
    public ResponseEntity<ResponseCore> deleteRole(@PathVariable Long id){
        ResponseCore responseCore = new ResponseCore();
        try {
            roleService.deleteRole(id);
            responseCore.setErrorState(false);
            responseCore.setResponseCode(HttpStatus.OK.value());
            responseCore.setMessage(Constants.SUCCESS);
        } catch (Exception e){
            log.info("Error", e.getLocalizedMessage());
            responseCore.setErrorState(true);
            responseCore.setMessage(Constants.UNEXPECTED_ERROR);
            responseCore.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new ResponseEntity<ResponseCore>(responseCore, HttpStatus.valueOf(responseCore.getResponseCode()));
    }

    @ApiOperation(value = SwaggerConstants.GET_ROLE_BY_ID, httpMethod = SwaggerConstants.GET)
    @RequestMapping(value = Constants.PATH_GET_ROLE_BY_ID, method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.SUCCESS, response = RoleResponse.class) })
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable String id){
        RoleResponse roleResponse = new RoleResponse();
        Role role;
        try {
            role = roleService.getRoleById(Long.parseLong(id));
            roleResponse.setMessage(Constants.SUCCESS);
            roleResponse.setResponseCode(HttpStatus.OK.value());
            roleResponse.setRole(role);
        } catch (NumberFormatException e) {
            try {
                role = roleService.getRoleByName(id);
                roleResponse.setMessage(Constants.SUCCESS);
                roleResponse.setResponseCode(HttpStatus.OK.value());
                roleResponse.setRole(role);
            } catch (RoleNotFoundException e1) {
                log.info("Error", e1.getMessage());
                roleResponse.setMessage(e1.getMessage());
                roleResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } catch (RoleNotFoundException e) {
            log.info("Error", e.getMessage());
            roleResponse.setMessage(e.getMessage());
            roleResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new ResponseEntity<RoleResponse>(roleResponse, HttpStatus.valueOf(roleResponse.getResponseCode()));
    }

    @ApiOperation(value = SwaggerConstants.ADD_PERMISSION_TO_ROLE, httpMethod = SwaggerConstants.POST)
    @RequestMapping(value = Constants.PATH_ADD_PERMISSION_TO_ROLE, method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.SUCCESS, response = RoleResponse.class) })
    public ResponseEntity<RoleResponse> addNewPermissionToRole(@PathVariable Long id, @RequestBody Permission permission){
        RoleResponse roleResponse = new RoleResponse();
        Role role;
        try {
            role = roleService.addPermissionToRole(permission, id);
            roleResponse.setRole(role);
            roleResponse.setResponseCode(HttpStatus.OK.value());
            roleResponse.setMessage(Constants.SUCCESS);
        } catch (RoleNotFoundException e) {
            log.info("Error: " + e.getMessage());
            roleResponse.setMessage(e.getMessage());
            roleResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            roleResponse.setErrorState(true);
        } catch (Exception e){
            e.printStackTrace();
            log.info("Error: " + e.getMessage());
            roleResponse.setMessage(Constants.UNEXPECTED_ERROR);
            roleResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            roleResponse.setErrorState(true);
        }
        return new ResponseEntity<RoleResponse>(roleResponse, HttpStatus.valueOf(roleResponse.getResponseCode()));
    }

    @ApiOperation(value = SwaggerConstants.REMOVE_PERMISSION_FROM_ROLE, httpMethod = SwaggerConstants.DELETE)
    @RequestMapping(value = Constants.PATH_REMOVE_PERMISSION_FROM_ROLE, method = RequestMethod.DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.SUCCESS, response = RoleResponse.class) })
    public ResponseEntity<RoleResponse> removePermissonFromRole(@PathVariable Long id, @PathVariable Long permissionId){
        RoleResponse roleResponse = new RoleResponse();
        Role role;
        try {
            role = roleService.removePermissionFromRole(id, permissionId);
            roleResponse.setRole(role);
            roleResponse.setMessage(Constants.SUCCESS);
            roleResponse.setResponseCode(HttpStatus.OK.value());
        } catch (RoleNotFoundException e) {
            log.info("Error: " + e.getMessage());
            roleResponse.setMessage(e.getMessage());
            roleResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            roleResponse.setErrorState(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Error: " + e.getMessage());
            roleResponse.setMessage(Constants.UNEXPECTED_ERROR);
            roleResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            roleResponse.setErrorState(true);
        }
        return new ResponseEntity<RoleResponse>(roleResponse, HttpStatus.valueOf(roleResponse.getResponseCode()));
    }

}
