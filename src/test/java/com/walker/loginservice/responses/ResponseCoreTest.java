package com.walker.loginservice.responses;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rettwalker on 2/10/17.
 */
public class ResponseCoreTest {

    private ResponseCore responseCore;
    private String MESSAGE = "ERROR";
    private int RESPONSECODE = 1;

    @Before
    public void setUp() throws Exception {
        responseCore = new ResponseCore();
        responseCore.setResponseCode(RESPONSECODE);
        responseCore.setMessage(MESSAGE);
        responseCore.setErrorState(true);
    }

    @Test
    public void getResponseCode() throws Exception {
        assertEquals(RESPONSECODE, responseCore.getResponseCode());
    }

    @Test
    public void setResponseCode() throws Exception {
        responseCore.setResponseCode(0);
        assertEquals(0, responseCore.getResponseCode());
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals(MESSAGE, responseCore.getMessage());
    }

    @Test
    public void setMessage() throws Exception {
        responseCore.setMessage(null);
        assertNull(responseCore.getMessage());
    }

    @Test
    public void isErrorState() throws Exception {
        assertTrue(responseCore.isErrorState());
    }

    @Test
    public void setErrorState() throws Exception {
        responseCore.setErrorState(false);
        assertFalse(responseCore.isErrorState());
    }

}