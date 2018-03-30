package com.maxvi.lifeblog.service.user;

import com.maxvi.lifeblog.service.dto.CurrentUserDto;
import com.maxvi.lifeblog.service.exception.UserAlreadyExistsException;
import com.maxvi.lifeblog.service.user.dto.SignupDto;


public interface UserService
{
    CurrentUserDto getCurrentUser();
    boolean isCurrentUserOrAdmin(Long userId);
    CurrentUserDto signUp(SignupDto signupDto) throws UserAlreadyExistsException;
}
