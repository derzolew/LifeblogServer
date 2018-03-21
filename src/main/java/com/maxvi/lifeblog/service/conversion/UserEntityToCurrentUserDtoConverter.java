package com.maxvi.lifeblog.service.conversion;

import com.maxvi.lifeblog.model.user.UserEntity;
import com.maxvi.lifeblog.service.dto.CurrentUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class UserEntityToCurrentUserDtoConverter implements Converter<UserEntity, CurrentUserDto>
{
    @Nullable
    @Override
    public CurrentUserDto convert(UserEntity userEntity)
    {
        CurrentUserDto currentUserDto = new CurrentUserDto();
        currentUserDto.setId(userEntity.getId());
        currentUserDto.setLogin(userEntity.getLogin());
        currentUserDto.setActivated(userEntity.isActivated());
        currentUserDto.setRole(userEntity.getRole());
        return currentUserDto;
    }
}
