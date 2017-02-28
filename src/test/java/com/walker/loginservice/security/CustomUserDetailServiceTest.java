package com.walker.loginservice.security;

import com.walker.loginservice.domain.Role;
import com.walker.loginservice.domain.User;
import com.walker.loginservice.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by jmw0705 on 2/7/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CustomUserDetailService.class)
public class CustomUserDetailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailService customUserDetailService = new CustomUserDetailService(userRepository);

    private User user;

    private String USERNAME = "admin";

    private String PASSWORD = "GUEST";

    private Set<Role> ROLES;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setUserName(USERNAME);
        user.setActivated(true);
        user.setPassword(PASSWORD);
        ROLES = new HashSet<>();
        user.setRoles(ROLES);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loadUserByUsername() throws Exception {
        when(userRepository.findByUserName(USERNAME.toUpperCase())).thenReturn(user);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(USERNAME);
        verify(userRepository, times(1)).findByUserName(USERNAME.toUpperCase());
        assertNotNull(userDetails);
        assertEquals(USERNAME, userDetails.getUsername());
        assertEquals(PASSWORD, userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    public void loadUserByUsernameReturnNull() throws Exception {
        thrown.expect(UsernameNotFoundException.class);
        when(userRepository.findByUserName(USERNAME)).thenReturn(null);
        customUserDetailService.loadUserByUsername(USERNAME);
        verify(userRepository, times(1)).findByUserName(USERNAME);
    }

    @Test
    public void loadUserByUsernameThrowException() throws Exception {
        when(userRepository.findByUserName(USERNAME.toUpperCase())).thenThrow(Exception.class);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(USERNAME);
        verify(userRepository, times(1)).findByUserName(USERNAME.toUpperCase());

        assertNull(userDetails);
    }

    @Test
    public void getPermissions() throws Exception {
        List<GrantedAuthority> authorityList = Whitebox.invokeMethod(customUserDetailService, "getPermissions", ROLES);

        assertNotNull(authorityList);

    }

}