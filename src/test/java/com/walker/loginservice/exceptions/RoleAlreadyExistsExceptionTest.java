package com.walker.loginservice.exceptions;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;

/**
 * Created by rettwalker on 2/10/17.
 */
public class RoleAlreadyExistsExceptionTest {

    private RoleAlreadyExistsException roleAlreadyExistsException;
    private String MESSAGE = "ERRORS";

    @Before
    public void setUp() throws Exception {
        roleAlreadyExistsException = new RoleAlreadyExistsException(MESSAGE);
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals(MESSAGE, roleAlreadyExistsException.getMessage());
    }

    @Test
    public void getResponseCode() throws Exception {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), roleAlreadyExistsException.getResponseCode());
    }

}