package com.walker.loginservice.exceptions;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;

/**
 * Created by jmw0705 on 2/17/17.
 */
public class PermissionNotFoundExceptionTest {

    private PermissionNotFoundException permissionNotFoundException;
    private String MESSAGE = "TEST";

    @Before
    public void setUp() throws Exception {
        permissionNotFoundException = new PermissionNotFoundException(MESSAGE);
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals(MESSAGE, permissionNotFoundException.getMessage());
    }

    @Test
    public void getResponseCode() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND.value(), permissionNotFoundException.getResponseCode());
    }

}