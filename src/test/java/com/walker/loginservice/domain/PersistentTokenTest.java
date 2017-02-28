package com.walker.loginservice.domain;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by rettwalker on 2/6/17.
 */
public class PersistentTokenTest {

    private PersistentToken persistentToken;

    private String SERIES = "1234";
    private String SERIES2 = "4321";

    private String TOKEN = "A";
    private String TOKEN2 = "B";

    private String USERNAME = "ADMIN";
    private String USERNAME2 = "USER";

    private Timestamp LASTUSED = Timestamp.valueOf(LocalDateTime.MAX);
    private Timestamp LASTUSED2 = Timestamp.valueOf(LocalDateTime.MIN);

    @Before
    public void setUp() throws Exception {
        persistentToken = new PersistentToken();
        persistentToken.setSeries(SERIES);
        persistentToken.setUserName(USERNAME);
        persistentToken.setToken(TOKEN);
        persistentToken.setLastUsed(LASTUSED);
    }

    @Test
    public void getSeries() throws Exception {
        assertEquals(SERIES, persistentToken.getSeries());
    }

    @Test
    public void setSeries() throws Exception {
        persistentToken.setSeries(SERIES2);
        assertEquals(SERIES2, persistentToken.getSeries());
    }

    @Test
    public void getToken() throws Exception {
        assertEquals(TOKEN, persistentToken.getToken());
    }

    @Test
    public void setToken() throws Exception {
        persistentToken.setToken(TOKEN2);
        assertEquals(TOKEN2, persistentToken.getToken());
    }

    @Test
    public void getUserName() throws Exception {
        assertEquals(USERNAME, persistentToken.getUserName());
    }

    @Test
    public void setUserName() throws Exception {
        persistentToken.setUserName(USERNAME2);
        assertEquals(USERNAME2, persistentToken.getUserName());
    }

    @Test
    public void getLastUsed() throws Exception {
        assertEquals(LASTUSED, persistentToken.getLastUsed());
    }

    @Test
    public void setLastUsed() throws Exception {
        persistentToken.setLastUsed(LASTUSED2);
        assertEquals(LASTUSED2, persistentToken.getLastUsed());
    }

}