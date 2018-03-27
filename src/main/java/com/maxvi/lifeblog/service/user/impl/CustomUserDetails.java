package com.maxvi.lifeblog.service.user.impl;

import com.maxvi.lifeblog.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails
{

    private UserEntity user;

    public CustomUserDetails(UserEntity user)
    {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<SimpleGrantedAuthority> list = new ArrayList<>(1);
        list.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return list;
    }

    @Override
    public boolean isEnabled()
    {
        return user.isActivated();
    }

    @Override
    public String getUsername()
    {
        return user.getLogin();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    public UserEntity getUserEntity()
    {
        return user;
    }
}
