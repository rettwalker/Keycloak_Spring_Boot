package com.walker.loginservice.controllers;

import com.walker.loginservice.domain.Permission;
import com.walker.loginservice.domain.Role;
import com.walker.loginservice.repository.PermissionRepository;
import com.walker.loginservice.repository.RoleRepository;
import com.walker.loginservice.responses.AllRolesResponse;
import com.walker.loginservice.responses.ResponseCore;
import com.walker.loginservice.responses.RoleResponse;
import com.walker.loginservice.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by rettwalker on 2/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoleControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PermissionRepository permissionRepository;

    private Role role;
    private Permission permission;
    private List<Permission> PERMISSIONS;
    private Long PERMISSION_ID = 1L;

    private Long ID = 4L;
    private String NAME = "DOG";

    @Before
    public void setUp() throws Exception {
        role = new Role();
        PERMISSIONS = new ArrayList<>();

        role.setName(NAME);
        role.setPermissions(PERMISSIONS);

        permission = new Permission();
        permission.setName(NAME);

    }

    @Test
    @WithUserDetails("ADMIN")
    public void addNewRole() throws Exception {
        Role newRole = new Role();
        newRole.setId(ID);
        newRole.setName(NAME);
        newRole.setPermissions(PERMISSIONS);

        when(roleRepository.findOneByName(NAME)).thenReturn(null);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(newRole);

        ResponseEntity responseEntity = testRestTemplate.postForEntity(Constants.PATH_CREATE_NEW_ROLE, role, RoleResponse.class);
        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        verify(roleRepository, times(1)).findOneByName(NAME);
        verify(roleRepository, times(1)).save(Matchers.any(Role.class));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());

        assertNotNull(roleResponse);
        assertEquals(Constants.SUCCESS, roleResponse.getMessage());
        assertFalse(roleResponse.isErrorState());
        assertNotNull(roleResponse.getRole());
        assertNotNull(roleResponse.getRole().getId());
        assertEquals(ID, roleResponse.getRole().getId());
        permission = new Permission();
        permission.setName(NAME);
    }

    @Test
    @WithUserDetails(value = "ADMIN")
    public void addNewRoleThrowRoleAlreadyExists() throws Exception {
        role.setId(ID);

        when(roleRepository.findOneByName(NAME)).thenReturn(role);

        ResponseEntity responseEntity = testRestTemplate.postForEntity(Constants.PATH_CREATE_NEW_ROLE, role, RoleResponse.class);
        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        verify(roleRepository, times(1)).findOneByName(NAME);
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,responseEntity.getStatusCode());

        assertNotNull(roleResponse);
        assertEquals(Constants.ROLE_ALREADY_EXISTS, roleResponse.getMessage());
        assertTrue(roleResponse.isErrorState());
        assertNull(roleResponse.getRole());
    }

    @Test
    @WithUserDetails(value = "ADMIN")
    public void addNewRoleThrowGeneralException() throws Exception {
        role.setId(ID);

        when(roleRepository.findOneByName(NAME)).thenThrow(Exception.class);

        ResponseEntity responseEntity = testRestTemplate.postForEntity(Constants.PATH_CREATE_NEW_ROLE, role, RoleResponse.class);
        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        verify(roleRepository, times(1)).findOneByName(NAME);
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,responseEntity.getStatusCode());

        assertNotNull(roleResponse);
        assertEquals(Constants.UNEXPECTED_ERROR, roleResponse.getMessage());
        assertTrue(roleResponse.isErrorState());
        assertNull(roleResponse.getRole());
    }

    @Test
    public void findAllRoles() throws Exception {

        when(roleRepository.findAll()).thenReturn(new ArrayList<Role>());

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_ALL_ROLES, AllRolesResponse.class);

        verify(roleRepository, times(1)).findAll();

        AllRolesResponse allRolesResponse = (AllRolesResponse) responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(allRolesResponse);
        assertEquals(HttpStatus.OK.value(), allRolesResponse.getResponseCode());
        assertEquals(Constants.SUCCESS, allRolesResponse.getMessage());
        assertFalse(allRolesResponse.isErrorState());

    }

    @Test
    public void findAllRolesThrowsException() throws Exception {

        when(roleRepository.findAll()).thenThrow(Exception.class);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_ALL_ROLES, AllRolesResponse.class);

        verify(roleRepository, times(1)).findAll();

        AllRolesResponse allRolesResponse = (AllRolesResponse) responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        assertNotNull(allRolesResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), allRolesResponse.getResponseCode());
        assertEquals(Constants.UNEXPECTED_ERROR, allRolesResponse.getMessage());
        assertTrue(allRolesResponse.isErrorState());

    }

    @Test
    public void deleteRole() throws Exception {

        ResponseEntity responseEntity = testRestTemplate.exchange(Constants.PATH_DELETE_ROLE,
                HttpMethod.DELETE,
                new HttpEntity<Object>(new HttpHeaders()),
                ResponseCore.class,
                ID);

        verify(roleRepository, times(1)).delete(ID);
        ResponseCore responseCore = (ResponseCore) responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(responseCore);
        assertEquals(HttpStatus.OK.value(), responseCore.getResponseCode());
        assertEquals(Constants.SUCCESS, responseCore.getMessage());

    }

    @Test
    public void getRoleById() throws Exception {
        role.setId(ID);
        when(roleRepository.findOne(ID)).thenReturn(role);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_ROLE_BY_ID,RoleResponse.class, ID);
        verify(roleRepository, times(1)).findOne(ID);

        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(roleResponse);
        assertEquals(HttpStatus.OK.value(), roleResponse.getResponseCode());
        assertEquals(Constants.SUCCESS, roleResponse.getMessage());
        assertNotNull(roleResponse.getRole());
        assertEquals(ID, roleResponse.getRole().getId());
        assertEquals(NAME, roleResponse.getRole().getName());
    }

    @Test
    public void getRoleByIdThrowRoleNotFoundException() throws Exception {
        role.setId(ID);
        when(roleRepository.findOne(ID)).thenReturn(null);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_ROLE_BY_ID,RoleResponse.class, ID);
        verify(roleRepository, times(1)).findOne(ID);

        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        assertNotNull(roleResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), roleResponse.getResponseCode());
        assertNotEquals(Constants.SUCCESS, roleResponse.getMessage());
        assertNull(roleResponse.getRole());
    }

    @Test
    public void getRoleByIdWithName() throws Exception {
        role.setId(ID);
        when(roleRepository.findOneByName(NAME)).thenReturn(role);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_ROLE_BY_ID,RoleResponse.class, NAME);
        verify(roleRepository, times(1)).findOneByName(NAME);

        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(roleResponse);
        assertEquals(HttpStatus.OK.value(), roleResponse.getResponseCode());
        assertEquals(Constants.SUCCESS, roleResponse.getMessage());
        assertNotNull(roleResponse.getRole());
        assertEquals(ID, roleResponse.getRole().getId());
        assertEquals(NAME, roleResponse.getRole().getName());
    }

    @Test
    public void getRoleByIdWithNameThrowRoleNotFoundException() throws Exception {
        role.setId(ID);
        when(roleRepository.findOneByName(NAME)).thenReturn(null);

        ResponseEntity responseEntity = testRestTemplate.getForEntity(Constants.PATH_GET_ROLE_BY_ID,RoleResponse.class, NAME);
        verify(roleRepository, times(1)).findOneByName(NAME);

        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        assertNotNull(roleResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), roleResponse.getResponseCode());
        assertNotEquals(Constants.SUCCESS, roleResponse.getMessage());
        assertNull(roleResponse.getRole());
    }

    @Test
    public void addNewPermissionToRole() throws Exception {
        role.setId(ID);
        role.setPermissions(new ArrayList<>());
        when(roleRepository.findOne(ID)).thenReturn(role);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        ResponseEntity responseEntity = testRestTemplate.postForEntity(Constants.PATH_ADD_PERMISSION_TO_ROLE, permission, RoleResponse.class,ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(1)).save(Matchers.any(Role.class));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        assertNotNull(roleResponse);
        assertEquals(HttpStatus.OK.value(), roleResponse.getResponseCode());
        assertEquals(Constants.SUCCESS, roleResponse.getMessage());

        assertNotNull(roleResponse.getRole());
        assertEquals(ID, roleResponse.getRole().getId());
        assertEquals(1, roleResponse.getRole().getPermissions().size());

        assertFalse(roleResponse.isErrorState());

    }

    @Test
    public void addNewPermissionToRoleThrowRoleNotFound() throws Exception {
        role.setId(ID);
        role.setPermissions(new ArrayList<>());
        when(roleRepository.findOne(ID)).thenReturn(null);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        ResponseEntity responseEntity = testRestTemplate.postForEntity(Constants.PATH_ADD_PERMISSION_TO_ROLE, permission, RoleResponse.class,ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        assertNotNull(roleResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), roleResponse.getResponseCode());
        assertNotEquals(Constants.SUCCESS, roleResponse.getMessage());
        assertTrue(roleResponse.isErrorState());

        assertNull(roleResponse.getRole());
    }

    @Test
    public void addNewPermissionToRoleThrowException() throws Exception {
        role.setId(ID);
        role.setPermissions(new ArrayList<>());
        when(roleRepository.findOne(ID)).thenThrow(Exception.class);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        ResponseEntity responseEntity = testRestTemplate.postForEntity(Constants.PATH_ADD_PERMISSION_TO_ROLE, permission, RoleResponse.class,ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        assertNotNull(roleResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), roleResponse.getResponseCode());
        assertEquals(Constants.UNEXPECTED_ERROR, roleResponse.getMessage());
        assertTrue(roleResponse.isErrorState());

        assertNull(roleResponse.getRole());
    }

    @Test
    public void removePermissonFromRole() throws Exception {
        role.setId(ID);

        when(roleRepository.findOne(ID)).thenReturn(role);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        when(permissionRepository.findOne(PERMISSION_ID)).thenReturn(permission);

        ResponseEntity responseEntity = testRestTemplate.exchange(Constants.PATH_REMOVE_PERMISSION_FROM_ROLE,
                HttpMethod.DELETE,
                new HttpEntity<Object>(new HttpHeaders()),
                RoleResponse.class,
                ID, PERMISSION_ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(1)).save(Matchers.any(Role.class));

        verify(permissionRepository, times(1)).findOne(PERMISSION_ID);

        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(roleResponse);

        assertEquals(Constants.SUCCESS, roleResponse.getMessage());
        assertEquals(HttpStatus.OK.value(), roleResponse.getResponseCode());
        assertFalse(roleResponse.isErrorState());

        assertNotNull(roleResponse.getRole());
        assertEquals(ID, roleResponse.getRole().getId());
        assertEquals(0, roleResponse.getRole().getPermissions().size());
    }

    @Test
    public void removePermissonFromRoleThrowRoleNotFound() throws Exception {

        role.setId(ID);

        when(roleRepository.findOne(ID)).thenReturn(null);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        when(permissionRepository.findOne(PERMISSION_ID)).thenReturn(permission);

        ResponseEntity responseEntity = testRestTemplate.exchange(Constants.PATH_REMOVE_PERMISSION_FROM_ROLE,
                HttpMethod.DELETE,
                new HttpEntity<Object>(new HttpHeaders()),
                RoleResponse.class,
                ID, PERMISSION_ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        verify(permissionRepository, times(0)).findOne(PERMISSION_ID);

        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        assertNotNull(roleResponse);

        assertNotEquals(Constants.SUCCESS, roleResponse.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), roleResponse.getResponseCode());
        assertTrue(roleResponse.isErrorState());

        assertNull(roleResponse.getRole());
    }

    @Test
    public void removePermissonFromRoleThrowException() throws Exception {

        role.setId(ID);

        when(roleRepository.findOne(ID)).thenThrow(Exception.class);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        when(permissionRepository.findOne(PERMISSION_ID)).thenReturn(permission);

        ResponseEntity responseEntity = testRestTemplate.exchange(Constants.PATH_REMOVE_PERMISSION_FROM_ROLE,
                HttpMethod.DELETE,
                new HttpEntity<Object>(new HttpHeaders()),
                RoleResponse.class,
                ID, PERMISSION_ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        verify(permissionRepository, times(0)).findOne(PERMISSION_ID);

        RoleResponse roleResponse = (RoleResponse) responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        assertNotNull(roleResponse);

        assertEquals(Constants.UNEXPECTED_ERROR, roleResponse.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), roleResponse.getResponseCode());
        assertTrue(roleResponse.isErrorState());

        assertNull(roleResponse.getRole());
    }
}