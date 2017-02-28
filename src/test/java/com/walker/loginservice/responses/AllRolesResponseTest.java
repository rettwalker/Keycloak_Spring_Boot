package com.walker.loginservice.responses;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jmw0705 on 2/13/17.
 */
public class AllRolesResponseTest {
    private AllRolesResponse allRolesResponse;


    @Before
    public void setUp() throws Exception {
        allRolesResponse = new AllRolesResponse();
        allRolesResponse.setRoles(new ArrayList<>());
    }

    @Test
    public void getRoles() throws Exception {
        assertNotNull(allRolesResponse.getRoles());
    }

    @Test
    public void setRoles() throws Exception {
        allRolesResponse.setRoles(null);
        assertNull(allRolesResponse.getRoles());
    }

}