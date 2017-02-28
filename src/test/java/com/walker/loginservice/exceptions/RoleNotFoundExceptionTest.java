package com.walker.loginservice.exceptions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jmw0705 on 2/13/17.
 */
public class RoleNotFoundExceptionTest {

    private RoleNotFoundException roleNotFoundException;
    private String MESSAGE = "MESSAGE";
    private int RESPONSECODE = 1;

    @Before
    public void setUp() throws Exception {
        roleNotFoundException = new RoleNotFoundException(MESSAGE);
        roleNotFoundException.setResponseCode(RESPONSECODE);
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals(MESSAGE, roleNotFoundException.getMessage());
    }

    @Test
    public void getResponseCode() throws Exception {
        assertEquals(RESPONSECODE, roleNotFoundException.getResponseCode());
    }

    @Test
    public void setResponseCode() throws Exception {
        roleNotFoundException.setResponseCode(0);
        assertEquals(0, roleNotFoundException.getResponseCode());
    }

}