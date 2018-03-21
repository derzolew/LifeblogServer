package com.maxvi.lifeblog.service.user.impl;

import com.maxvi.lifeblog.model.profile.ProfileEntity;
import com.maxvi.lifeblog.model.user.UserEntity;
import com.maxvi.lifeblog.repository.ProfileRepository;
import com.maxvi.lifeblog.repository.UserRepository;
import com.maxvi.lifeblog.service.dto.CurrentUserDto;
import com.maxvi.lifeblog.service.dto.ProfileDto;
import com.maxvi.lifeblog.service.user.ProfileService;
import com.maxvi.lifeblog.service.user.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService
{
    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "profileRepository")
    private ProfileRepository profileRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "conversionService")
    private ConversionService conversionService;

    @Override
    public ProfileDto getProfileById(Long userId)
    {
        ProfileEntity profileEntity = profileRepository.findOneByUserId(userId);
        if (profileEntity != null)
        {
            return conversionService.convert(profileEntity, ProfileDto.class);
        }
        return null;
    }

    @Override
    @Transactional
    public ProfileDto updateProfileById(Long userId, ProfileDto newProfileDto)
    {
        ProfileEntity profile = profileRepository.findOneByUserId(userId);
        if (profile == null)
        {
            profile = new ProfileEntity();
            profile.setUser(userRepository.findById(userId).get());
        }
        profile.setBirthday(newProfileDto.getBirthday());
        profile.setDescription(newProfileDto.getDescription());
        profile.setFirstName(newProfileDto.getFirstName());
        profile.setLastName(newProfileDto.getLastName());
        profile.setPhoneNumber(newProfileDto.getPhoneNumber());
        profile = profileRepository.save(profile);
        return conversionService.convert(profile, ProfileDto.class);
    }
}
