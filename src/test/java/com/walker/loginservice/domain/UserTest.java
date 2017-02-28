package com.walker.loginservice.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by jmw0705 on 2/7/17.
 */
public class UserTest {

    private User user;

    private Long ID = 1L;
    private String USERNAME = "ADMIN";
    private String EMAIL = "EMAIL";
    private String FIRSTNAME = "JOHN";
    private String LASTNAME = "DOE";
    private String PASSWORD = "GUEST";

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setEmail(EMAIL);
        user.setId(ID);
        user.setUserName(USERNAME);
        user.setFirstName(FIRSTNAME);
        user.setLastName(LASTNAME);
        user.setActivated(true);
        user.setPassword(PASSWORD);
        user.setRoles(new HashSet());
    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, user.getId());
    }

    @Test
    public void setId() throws Exception {
        user.setId(null);
        assertNull(user.getId());
    }

    @Test
    public void getUserName() throws Exception {
        assertEquals(USERNAME, user.getUserName());
    }

    @Test
    public void setUserName() throws Exception {
        user.setUserName(null);
        assertNull(user.getUserName());
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    public void setEmail() throws Exception {
        user.setEmail(null);
        assertNull(user.getEmail());
    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals(FIRSTNAME, user.getFirstName());
    }

    @Test
    public void setFirstName() throws Exception {
        user.setFirstName(null);
        assertNull(user.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals(LASTNAME, user.getLastName());
    }

    @Test
    public void setLastName() throws Exception {
        user.setLastName(null);
        assertNull(user.getLastName());
    }

    @Test
    public void isActivated() throws Exception {
        assertTrue(user.isActivated());
    }

    @Test
    public void setActivated() throws Exception {
        user.setActivated(false);
        assertFalse(user.isActivated());
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void setPassword() throws Exception {
        user.setPassword(null);
        assertNull(user.getPassword());
    }

    @Test
    public void getGrantedAuthoritySet() throws Exception {
        assertNotNull(user.getRoles());
    }

    @Test
    public void setGrantedAuthoritySet() throws Exception {
        user.setRoles(null);
        assertNull(user.getRoles());
    }

}