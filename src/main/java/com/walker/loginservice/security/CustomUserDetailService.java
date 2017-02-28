package com.walker.loginservice.security;

import com.walker.loginservice.domain.Role;
import com.walker.loginservice.domain.User;
import com.walker.loginservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jmw0705 on 2/7/17.
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        User user;
        try {
            user = userRepository.findByUserName(username.toUpperCase());
        } catch (Exception e) {
            log.debug(e.getMessage());
            return  userDetails;
        }

        if(user == null){
            log.info("Username " + username + " not found");
            throw new UsernameNotFoundException("Username " + username + " not found");
        } else {
            userDetails = new org.springframework.security.core.userdetails.User(user.getUserName().toLowerCase(),
                    user.getPassword(), user.isActivated(), true, true, true,
                    getPermissions(user.getRoles()));
        }
        return userDetails;
    }

    private List<GrantedAuthority> getPermissions(Set<Role> roles){
        List<GrantedAuthority> permissions = new ArrayList<>();
        for(Role role: roles){
            permissions.addAll(role.getPermissions());
        }
        return permissions;
    }
}
