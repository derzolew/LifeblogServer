package com.maxvi.lifeblog.service.user;

import com.maxvi.lifeblog.model.ProfileEntity;
import com.maxvi.lifeblog.service.user.dto.ProfileDto;

public interface ProfileService
{
    ProfileDto getProfileById(Long userId);
    ProfileDto updateProfileById(Long userId, ProfileDto newProfileDto);
    ProfileEntity getCurrentUserProfile();
}
