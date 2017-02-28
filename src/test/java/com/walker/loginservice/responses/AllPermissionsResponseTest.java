package com.walker.loginservice.responses;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jmw0705 on 2/15/17.
 */
public class AllPermissionsResponseTest {

    private AllPermissionsResponse allPermissionsResponse;

    @Before
    public void setUp() throws Exception {
        allPermissionsResponse = new AllPermissionsResponse();
        allPermissionsResponse.setPermissions(new ArrayList<>());
    }

    @Test
    public void getPermissions() throws Exception {
        assertNotNull(allPermissionsResponse.getPermissions());
    }

    @Test
    public void setPermissions() throws Exception {
        allPermissionsResponse.setPermissions(null);
        assertNull(allPermissionsResponse.getPermissions());
    }

}