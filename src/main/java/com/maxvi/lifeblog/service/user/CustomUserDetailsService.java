package com.maxvi.lifeblog.service.user;

import com.maxvi.lifeblog.model.user.UserEntity;
import com.maxvi.lifeblog.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        UserEntity user = userRepository.findByLogin(login);
        return new CustomUserDetails(user);
    }
}
