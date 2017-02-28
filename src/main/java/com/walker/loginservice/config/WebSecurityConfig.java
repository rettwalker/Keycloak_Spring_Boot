package com.walker.loginservice.config;

import com.walker.loginservice.repository.UserRepository;
import com.walker.loginservice.security.AjaxAuthenticationFailureHandler;
import com.walker.loginservice.security.AjaxAuthenticationSuccessHandler;
import com.walker.loginservice.security.CustomUserDetailService;
import com.walker.loginservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


/**
 * Created by rettwalker on 2/4/17.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Profile(value = {"dev", "default"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserRepository userRepository;

    private PersistentTokenRepository persistentTokenRepository;

    private Environment environment;

    private CustomUserDetailService customUserDetailService;

    @Autowired
    public WebSecurityConfig(CustomUserDetailService customUserDetailService, UserRepository userRepository, PersistentTokenRepository persistentTokenRepository, Environment environment){
        this.customUserDetailService = customUserDetailService;
        this.userRepository = userRepository;
        this.persistentTokenRepository = persistentTokenRepository;
        this.environment = environment;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling()
        .and()
            .authorizeRequests()
            .anyRequest().fullyAuthenticated().antMatchers("/RoleServices/**").hasAuthority(Constants.PERM_USER_MANAGEMENT)
            .anyRequest().fullyAuthenticated().antMatchers("/PermissionServices/**").hasAuthority(Constants.PERM_USER_MANAGEMENT)
        .and()
            .formLogin()
            .loginProcessingUrl("/api")
            .defaultSuccessUrl("/api")
            .successHandler(new AjaxAuthenticationSuccessHandler(userRepository))
            .failureHandler(new AjaxAuthenticationFailureHandler())
            .usernameParameter("userName")
            .passwordParameter("password")
        .and()
            .rememberMe()
            .rememberMeParameter("rememberMe")
            .tokenRepository(persistentTokenRepository)
            .rememberMeCookieName("remember-me")
            .userDetailsService(customUserDetailService)
            .tokenValiditySeconds(86400);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }

}
