package com.maxvi.lifeblog.service.user;

import com.maxvi.lifeblog.service.dto.ProfileDto;

public interface ProfileService
{
    ProfileDto getProfileById(Long userId);
    ProfileDto updateProfileById(Long userId, ProfileDto newProfileDto);
}