package com.walker.loginservice.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by jmw0705 on 2/10/17.
 */
public class RoleTest {

    private Role role;

    private Long ID = 1L;
    private String NAME = "ADMIN";
    private Set USERS;
    private Set PRIVILEGES;


    @Before
    public void setUp() throws Exception {
        role = new Role();
        USERS = new HashSet();
        PRIVILEGES = new HashSet();
        role.setId(ID);
        role.setName(NAME);
        role.setPermissions(PRIVILEGES);
        role.setUsers(USERS);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, role.getId());
    }

    @Test
    public void setId() throws Exception {
        role.setId(null);
        assertNull(role.getId());
    }

    @Test
    public void getUsers() throws Exception {
        assertNotNull(role.getUsers());
    }

    @Test
    public void setUsers() throws Exception {
        role.setUsers(null);
        assertNull(role.getUsers());
    }

    @Test
    public void getPermissions() throws Exception {
        assertNotNull(role.getPermissions());
    }

    @Test
    public void setPermissions() throws Exception {
        role.setPermissions(null);
        assertNull(role.getPermissions());
    }

    @Test
    public void getAuthority() throws Exception {
        assertEquals("ROLE_"+NAME, role.getAuthority());
    }

    @Test
    public void getName() throws Exception {
        assertEquals(NAME, role.getName());
    }

    @Test
    public void setName() throws Exception {
        role.setName(null);
        assertNull(role.getName());
    }
}