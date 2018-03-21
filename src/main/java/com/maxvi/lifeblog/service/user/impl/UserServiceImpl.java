package com.maxvi.lifeblog.service.user.impl;

import com.maxvi.lifeblog.model.user.UserEntity;
import com.maxvi.lifeblog.service.dto.CurrentUserDto;
import com.maxvi.lifeblog.service.security.SecurityService;
import com.maxvi.lifeblog.service.user.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService
{
    @Resource(name = "securityService")
    private SecurityService securityService;

    @Resource(name = "conversionService")
    private ConversionService conversionService;

    @Override
    public CurrentUserDto getCurrentUser()
    {
        UserEntity userEntity = securityService.getCurrentUserEntity();
        if (userEntity != null)
        {
            return conversionService.convert(userEntity, CurrentUserDto.class);
        }
        return null;
    }
}
