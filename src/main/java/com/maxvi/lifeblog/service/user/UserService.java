package com.maxvi.lifeblog.service.user;

import com.maxvi.lifeblog.service.dto.CurrentUserDto;


public interface UserService
{
    CurrentUserDto getCurrentUser();

    boolean isCurrentUserOrAdmin(Long userId);
}
