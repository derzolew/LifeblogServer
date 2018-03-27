package com.maxvi.lifeblog.service.security;

import com.maxvi.lifeblog.model.UserEntity;

public interface SecurityService
{
    String getCurrentUserLogin();
    UserEntity getCurrentUserEntity();
    Long getCurrentUserId();
}
