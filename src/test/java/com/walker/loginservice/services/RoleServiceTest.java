package com.walker.loginservice.services;

import com.walker.loginservice.domain.Permission;
import com.walker.loginservice.domain.Role;
import com.walker.loginservice.exceptions.RoleAlreadyExistsException;
import com.walker.loginservice.exceptions.RoleNotFoundException;
import com.walker.loginservice.repository.PermissionRepository;
import com.walker.loginservice.repository.RoleRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.reflect.Whitebox.invokeMethod;

/**
 * Created by rettwalker on 2/10/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RoleService.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private RoleService roleService = new RoleService(roleRepository, permissionRepository);

    @Rule
    private ExpectedException expectedException = ExpectedException.none();

    private Role role;
    private List<Permission> PERMISSIONS;
    private Long PERMISSION_ID = 1L;
    private Permission permission;
    private String NAME = "ADMIN";
    private Long ID = 4L;

    @Before
    public void setUp() throws Exception {
        role = new Role();
        PERMISSIONS = new ArrayList<>();
        permission = new Permission();
        permission.setName(NAME);
        PERMISSIONS.add(permission);

        role.setName(NAME);
        role.setPermissions(PERMISSIONS);
    }

    @Test
    public void addNewRole() throws Exception {
        String roleName = "DOG";

        Role newRole = new Role();
        newRole.setId(ID);
        newRole.setName(NAME);
        newRole.setPermissions(PERMISSIONS);


        when(roleRepository.findOneByName(NAME)).thenReturn(null);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(newRole);

        Role response = roleService.addNewRole(role);

        verify(roleRepository, times(1)).findOneByName(NAME);
        verify(roleRepository, times(1)).save(Matchers.any(Role.class));

        assertNotNull(role);
        assertEquals("ROLE_"+NAME, response.getAuthority());
        assertNotNull(response.getPermissions());
        assertEquals(1, response.getPermissions().size());
        assertNotNull(response.getId());
        assertEquals(ID, response.getId());
    }

    @Test
    public void addNewRoleThrowRoleExists() throws Exception {

        when(roleRepository.findOneByName(role.getName())).thenReturn(role);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        expectedException.expect(RoleAlreadyExistsException.class);

        Role newRole = roleService.addNewRole(role);

        verify(roleRepository, times(1)).findOneByName(role.getName());
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        assertNull(newRole);
    }

    @Test
    public void findAllRoles() throws Exception {
        when(roleRepository.findAll()).thenReturn(new ArrayList<Role>());

        List<Role> roles = roleService.findAll();

        verify(roleRepository, times(1)).findAll();

        assertNotNull(roles);
    }

    @Test
    public void deleteRole() throws Exception {
        invokeMethod(roleService, "deleteRole", ID);
        verify(roleRepository,times(1)).delete(Matchers.any(Long.class));
    }

    @Test
    public void getRoleById() throws Exception {
        role.setId(ID);
        when(roleRepository.findOne(ID)).thenReturn(role);
        Role role = roleService.getRoleById(ID);
        verify(roleRepository, times(1)).findOne(Matchers.any(Long.class));

        assertNotNull(role);
        assertEquals(ID, role.getId());
        assertEquals(NAME, role.getName());
    }

    @Test
    public void getRoleByIdThrowRoleNotFound() throws Exception {
        role.setId(ID);

        when(roleRepository.findOne(ID)).thenReturn(null);
        expectedException.expect(RoleNotFoundException.class);

        Role role = roleService.getRoleById(ID);

        verify(roleRepository, times(1)).findOne(Matchers.any(Long.class));
        assertNull(role);
    }

    @Test
    public void getRoleByName() throws Exception {
        role.setId(ID);
        when(roleRepository.findOneByName(NAME)).thenReturn(role);
        Role role = roleService.getRoleByName(NAME);
        verify(roleRepository, times(1)).findOneByName(Matchers.anyString());

        assertNotNull(role);
        assertEquals(ID, role.getId());
        assertEquals(NAME, role.getName());
    }

    @Test
    public void getRoleByNameThrowRoleNotFound() throws Exception {
        role.setId(ID);

        when(roleRepository.findOneByName(NAME)).thenReturn(null);
        expectedException.expect(RoleNotFoundException.class);

        Role role = roleService.getRoleByName(NAME);

        verify(roleRepository, times(1)).findOneByName(Matchers.anyString());
        assertNull(role);
    }

    @Test
    public void addPermissionToRole() throws Exception {
        role.setPermissions(new ArrayList<>());
        when(roleRepository.findOne(ID)).thenReturn(role);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        Role updatedRole = roleService.addPermissionToRole(permission, ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(1)).save(Matchers.any(Role.class));

        assertNotNull(updatedRole);

        assertEquals(role.getId(), updatedRole.getId());
        assertEquals(1, updatedRole.getPermissions().size());
        assertEquals(permission, updatedRole.getPermissions().iterator().next());
    }

    @Test
    public void addPermissionToRoleRoleNotFound() throws Exception {
        role.setPermissions(new ArrayList<>());
        when(roleRepository.findOne(ID)).thenReturn(null);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);
        expectedException.expect(RoleNotFoundException.class);

        Role updatedRole = roleService.addPermissionToRole(permission, ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        assertNull(updatedRole);
    }

    @Test
    public void removePermissionFromRole() throws Exception {
        role.setId(ID);

        when(roleRepository.findOne(ID)).thenReturn(role);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        when(permissionRepository.findOne(PERMISSION_ID)).thenReturn(permission);

        Role updatedRole = roleService.removePermissionFromRole(ID, PERMISSION_ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(1)).save(Matchers.any(Role.class));

        verify(permissionRepository, times(1)).findOne(PERMISSION_ID);

        assertNotNull(updatedRole);
        assertEquals(ID, updatedRole.getId());
        assertEquals(0, updatedRole.getPermissions().size());
    }

    @Test
    public void removePermissionFromRoleRoleNotFound() throws Exception {
        role.setId(ID);

        when(roleRepository.findOne(ID)).thenReturn(null);
        when(roleRepository.save(Matchers.any(Role.class))).thenReturn(role);

        when(permissionRepository.findOne(PERMISSION_ID)).thenReturn(permission);
        expectedException.expect(RoleNotFoundException.class);

        Role updatedRole = roleService.removePermissionFromRole(ID, PERMISSION_ID);

        verify(roleRepository, times(1)).findOne(ID);
        verify(roleRepository, times(0)).save(Matchers.any(Role.class));

        verify(permissionRepository, times(0)).findOne(PERMISSION_ID);

        assertNull(updatedRole);
    }

}