package com.walker.loginservice.services;

import com.walker.loginservice.domain.Permission;
import com.walker.loginservice.exceptions.PermissionNotFoundException;
import com.walker.loginservice.repository.PermissionRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by jmw0705 on 2/15/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PermissionService.class)
public class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionService permissionService = new PermissionService(permissionRepository);

    private Permission permission;
    private List<Permission> permissions;

    private Long ID = 1L;
    private String NAME = "USERS";

    @Rule
    private ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        permissions = new ArrayList<>();
        permission = new Permission();
        permission.setName(NAME);
    }

    @Test
    public void findAllPermissions() throws Exception {
        when(permissionRepository.findAll()).thenReturn(permissions);

        List<Permission> permissionList = permissionService.findAllPermissions();

        verify(permissionRepository, times(1)).findAll();

        assertNotNull(permissionList);
    }

    @Test
    public void findPermissionById() throws Exception {
        permission.setId(ID);
        when(permissionRepository.findOne(ID)).thenReturn(permission);

        Permission foundPermission = permissionService.findPermissionById(ID);

        verify(permissionRepository, times(1)).findOne(ID);

        assertNotNull(foundPermission);

        assertEquals(ID, permission.getId());
        assertEquals(NAME, permission.getAuthority());
    }

    @Test
    public void findPermissionByIdThrowPermissionNotFound() throws Exception {
        permission.setId(ID);
        when(permissionRepository.findOne(ID)).thenReturn(null);

        expectedException.expect(PermissionNotFoundException.class);
        Permission foundPermission = permissionService.findPermissionById(ID);

        verify(permissionRepository, times(1)).findOne(ID);

        assertNotNull(foundPermission);

        assertEquals(ID, permission.getId());
        assertEquals(NAME, permission.getAuthority());
    }



}