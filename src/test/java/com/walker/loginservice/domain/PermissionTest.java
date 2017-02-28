package com.walker.loginservice.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by rettwalker on 2/9/17.
 */
public class PermissionTest {
    private Permission permission;

    private Long ID = 1L;
    private String NAME = "BOO";
    private Set ROLES;

    @Before
    public void setUp() throws Exception {
        permission = new Permission();
        ROLES = new HashSet();
        permission.setId(ID);
        permission.setName(NAME);
        permission.setRoles(ROLES);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, permission.getId());
    }

    @Test
    public void setId() throws Exception {
        permission.setId(null);
        assertNull(permission.getId());
    }

    @Test
    public void getName() throws Exception {
        assertEquals(NAME, permission.getAuthority());
    }

    @Test
    public void setName() throws Exception {
        permission.setName(null);
        assertNull(permission.getAuthority());
    }

    @Test
    public void getRoles() throws Exception {
        assertNotNull(permission.getRoles());
    }

    @Test
    public void setRoles() throws Exception {
        permission.setRoles(null);
        assertNull(permission.getRoles());
    }

}