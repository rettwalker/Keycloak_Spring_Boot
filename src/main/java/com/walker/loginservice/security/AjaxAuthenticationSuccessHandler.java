package com.walker.loginservice.security;

import com.walker.loginservice.domain.Role;
import com.walker.loginservice.domain.User;
import com.walker.loginservice.repository.UserRepository;
import com.walker.loginservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(AjaxAuthenticationSuccessHandler.class);

    @Autowired
    public AjaxAuthenticationSuccessHandler(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws IOException, ServletException {
        User user;
        String login = authentication.getName();
        log.debug("Request to get login name:",login);
        try{
            user = userRepository.findByUserName(login);
            if(user == null){
                user = new User();
                user.setActivated(true);
                user.setUserName(login.toUpperCase());
                user.setPassword("notauserspassword");
                Set<Role> roleSet = new HashSet<>();
                roleSet.add(new Role(Constants.ROLE_USER));
                user.setRoles(roleSet);
                userRepository.save(user);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e){
            log.debug(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
