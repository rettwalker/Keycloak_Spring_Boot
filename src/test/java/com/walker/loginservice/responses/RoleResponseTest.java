package com.walker.loginservice.responses;

import com.walker.loginservice.domain.Role;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rettwalker on 2/11/17.
 */
public class RoleResponseTest {
    private RoleResponse roleResponse;
    private Role role;

    @Before
    public void setUp() throws Exception {
        roleResponse = new RoleResponse();
        role = new Role();

        roleResponse.setRole(role);
    }

    @Test
    public void getRole() throws Exception {
        assertEquals(role, roleResponse.getRole());
    }

    @Test
    public void setRole() throws Exception {
        roleResponse.setRole(null);
        assertNull(roleResponse.getRole());
    }

}