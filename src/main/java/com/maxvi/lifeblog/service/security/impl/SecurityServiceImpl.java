package com.maxvi.lifeblog.service.security.impl;

import com.maxvi.lifeblog.model.UserEntity;
import com.maxvi.lifeblog.repository.UserRepository;
import com.maxvi.lifeblog.service.security.SecurityService;
import com.maxvi.lifeblog.service.user.impl.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService
{
    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Override
    public String getCurrentUserLogin()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String login = null;
        if (authentication != null)
        {
            if (authentication.getPrincipal() instanceof UserDetails)
            {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                login = springSecurityUser.getUsername();
            }
            else if (authentication.getPrincipal() instanceof String)
            {
                login = (String) authentication.getPrincipal();
            }
        }
        return login;
    }

    @Override
    public UserEntity getCurrentUserEntity()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null)
        {
            if (authentication.getPrincipal() instanceof CustomUserDetails)
            {
                CustomUserDetails springSecurityUser = (CustomUserDetails) authentication.getPrincipal();
                return springSecurityUser.getUserEntity();
            }
            else if (authentication.getPrincipal() instanceof String)
            {
                return null;
            }
        }
        return null;
    }

    @Override
    public Long getCurrentUserId()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null)
        {
            if (authentication.getPrincipal() instanceof CustomUserDetails)
            {
                CustomUserDetails springSecurityUser = (CustomUserDetails) authentication.getPrincipal();
                return springSecurityUser.getUserEntity().getId();
            }
            else if (authentication.getPrincipal() instanceof String)
            {
                return null;
            }
        }
        return null;
    }
}
