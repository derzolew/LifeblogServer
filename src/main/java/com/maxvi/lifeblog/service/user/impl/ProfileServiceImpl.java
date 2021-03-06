package com.maxvi.lifeblog.service.user.impl;

import com.maxvi.lifeblog.model.ImageEntity;
import com.maxvi.lifeblog.model.ProfileEntity;
import com.maxvi.lifeblog.model.UserEntity;
import com.maxvi.lifeblog.repository.ImageRepository;
import com.maxvi.lifeblog.repository.ProfileRepository;
import com.maxvi.lifeblog.repository.UserRepository;
import com.maxvi.lifeblog.service.conversion.Converters;
import com.maxvi.lifeblog.service.image.ImageService;
import com.maxvi.lifeblog.service.user.dto.ProfileDto;
import com.maxvi.lifeblog.service.security.SecurityService;
import com.maxvi.lifeblog.service.user.ProfileService;
import com.maxvi.lifeblog.service.user.UserService;
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

    @Resource(name = "securityService")
    private SecurityService securityService;

    @Resource(name = "imageRepository")
    private ImageRepository imageRepository;

    @Override
    public ProfileDto getProfileById(Long userId)
    {
        ProfileEntity profileEntity = profileRepository.findByUser_Id(userId);
        if (profileEntity != null)
        {
            return Converters.profileEntityToDtoConverter(profileEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public ProfileDto updateProfileById(Long userId, ProfileDto newProfileDto)
    {
        ProfileEntity profile = profileRepository.findByUser_Id(userId);
        if (profile == null)
        {
            profile = new ProfileEntity();
            profile.setUser(userRepository.findById(userId).orElse(null));
        }
        profile.setBirthday(newProfileDto.getBirthday());
        profile.setDescription(newProfileDto.getDescription());
        profile.setFirstName(newProfileDto.getFirstName());
        profile.setLastName(newProfileDto.getLastName());
        profile.setPhoneNumber(newProfileDto.getPhoneNumber());
        if (newProfileDto.getPhotoName() != null)
        {
            ImageEntity imageEntity = imageRepository.findImageEntityByPublicFileName(newProfileDto.getPhotoName());
            profile.setPhoto(imageEntity);
        }
        profile = profileRepository.save(profile);
        return Converters.profileEntityToDtoConverter(profile);
    }

    @Override
    public ProfileEntity getCurrentUserProfile()
    {
        UserEntity userEntity = securityService.getCurrentUserEntity();
        return profileRepository.findByUser_Id(userEntity.getId());
    }
}
