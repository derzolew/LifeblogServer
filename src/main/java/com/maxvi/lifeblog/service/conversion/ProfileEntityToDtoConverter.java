package com.maxvi.lifeblog.service.conversion;

import com.maxvi.lifeblog.model.profile.ProfileEntity;
import com.maxvi.lifeblog.service.dto.ProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class ProfileEntityToDtoConverter implements Converter<ProfileEntity, ProfileDto>
{
    @Nullable
    @Override
    public ProfileDto convert(ProfileEntity profileEntity)
    {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profileEntity.getId());
        profileDto.setBirthday(profileEntity.getBirthday());
        profileDto.setDescription(profileEntity.getDescription());
        profileDto.setFirstName(profileEntity.getFirstName());
        profileDto.setLastName(profileEntity.getLastName());
        profileDto.setPhoneNumber(profileEntity.getPhoneNumber());
        return profileDto;
    }
}
