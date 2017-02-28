package com.walker.loginservice.controllers;

import com.walker.loginservice.domain.Permission;
import com.walker.loginservice.repository.PermissionRepository;
import com.walker.loginservice.responses.AllPermissionsResponse;
import com.walker.loginservice.responses.PermissionResponse;
import com.walker.loginservice.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by jmw0705 on 2/15/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PermissionControllerTest {

    private Permission permission;
    private List<Permission> permissions;

    private Long ID = 1L;
    private String NAME = "USERS";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private PermissionRepository permissionRepository;

    @Before
    public void setUp() throws Exception {
        permissions = new ArrayList<>();
        permission = new Permission();
        permission.setName(NAME);
    }

    @Test
    public void findAllPermissions() throws Exception {
        when(permissionRepository.findAll()).thenReturn(permissions);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_ALL_PERMISSIONS, AllPermissionsResponse.class);

        verify(permissionRepository, times(1)).findAll();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        AllPermissionsResponse allPermissionsResponse = (AllPermissionsResponse) responseEntity.getBody();

        assertNotNull(allPermissionsResponse);
        assertEquals(Constants.SUCCESS, allPermissionsResponse.getMessage());
        assertEquals(HttpStatus.OK.value(), allPermissionsResponse.getResponseCode());
        assertNotNull(allPermissionsResponse.getPermissions());

    }

    @Test
    public void findAllPermissionsThrowsException() throws Exception {
        when(permissionRepository.findAll()).thenThrow(Exception.class);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_ALL_PERMISSIONS, AllPermissionsResponse.class);

        verify(permissionRepository, times(1)).findAll();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        AllPermissionsResponse allPermissionsResponse = (AllPermissionsResponse) responseEntity.getBody();

        assertNotNull(allPermissionsResponse);
        assertEquals(Constants.UNEXPECTED_ERROR, allPermissionsResponse.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), allPermissionsResponse.getResponseCode());
        assertTrue(allPermissionsResponse.isErrorState());
        assertNull(allPermissionsResponse.getPermissions());

    }

    @Test
    public void getRoleById() throws Exception {
        when(permissionRepository.findOne(ID)).thenReturn(permission);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_PERMISSION, PermissionResponse.class,ID);

        verify(permissionRepository, times(1)).findOne(ID);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        PermissionResponse permissionResponse = (PermissionResponse) responseEntity.getBody();

        assertNotNull(permissionResponse);
        assertEquals(Constants.SUCCESS, permissionResponse.getMessage());
        assertEquals(HttpStatus.OK.value(), permissionResponse.getResponseCode());
        assertFalse(permissionResponse.isErrorState());

        assertNotNull(permissionResponse.getPermission());

    }

    @Test
    public void getRoleByIdThrowsException() throws Exception {
        when(permissionRepository.findOne(ID)).thenThrow(Exception.class);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_PERMISSION, PermissionResponse.class,ID);

        verify(permissionRepository, times(1)).findOne(ID);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        PermissionResponse permissionResponse = (PermissionResponse) responseEntity.getBody();

        assertNotNull(permissionResponse);
        assertEquals(Constants.UNEXPECTED_ERROR, permissionResponse.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), permissionResponse.getResponseCode());
        assertTrue(permissionResponse.isErrorState());

        assertNull(permissionResponse.getPermission());

    }

    @Test
    public void getRoleByIdThrowPermissionNotFoundException() throws Exception {
        when(permissionRepository.findOne(ID)).thenReturn(null);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_PERMISSION, PermissionResponse.class,ID);

        verify(permissionRepository, times(1)).findOne(ID);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        PermissionResponse permissionResponse = (PermissionResponse) responseEntity.getBody();

        assertNotNull(permissionResponse);
        assertNotEquals(Constants.SUCCESS, permissionResponse.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), permissionResponse.getResponseCode());
        assertTrue(permissionResponse.isErrorState());

        assertNull(permissionResponse.getPermission());

    }

}