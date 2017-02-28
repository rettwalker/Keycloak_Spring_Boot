package com.walker.loginservice.responses;

import com.walker.loginservice.domain.Permission;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jmw0705 on 2/15/17.
 */
public class PermissionResponseTest {

    private PermissionResponse permissionResponse;

    @Before
    public void setUp() throws Exception {
        permissionResponse = new PermissionResponse();
        permissionResponse.setPermission(new Permission());
    }

    @Test
    public void getPermission() throws Exception {
        assertNotNull(permissionResponse.getPermission());
    }

    @Test
    public void setPermission() throws Exception {
        permissionResponse.setPermission(null);
        assertNull(permissionResponse.getPermission());
    }

}