package com.walker.loginservice.security;

import com.walker.loginservice.domain.User;
import com.walker.loginservice.repository.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

/**
 * Created by jmw0705 on 2/7/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AjaxAuthenticationSuccessHandler.class)
public class AjaxAuthenticationSuccessHandlerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    Authentication authentication;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @InjectMocks
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler = new AjaxAuthenticationSuccessHandler(userRepository);

    private User user;
    private String USERNAME = "ADMIN";

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setUserName(USERNAME);
    }

    @Test
    public void onAuthenticationSuccess() throws Exception {
        when(userRepository.save(Matchers.any(User.class))).thenReturn(user);
        when(userRepository.findByUserName(Matchers.anyString())).thenReturn(user);
        when(authentication.getName()).thenReturn(USERNAME);

        ajaxAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);

        verify(userRepository, times(0)).save(Matchers.any(User.class));
        verify(userRepository, times(1)).findByUserName(Matchers.anyString());
    }

    @Test
    public void onAuthenticationSuccessUserNotFound() throws Exception {
        when(userRepository.save(Matchers.any(User.class))).thenReturn(user);
        when(userRepository.findByUserName(Matchers.anyString())).thenReturn(null);
        when(authentication.getName()).thenReturn(USERNAME);

        ajaxAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);

        verify(userRepository, times(1)).save(Matchers.any(User.class));
        verify(userRepository, times(1)).findByUserName(Matchers.anyString());
    }

}