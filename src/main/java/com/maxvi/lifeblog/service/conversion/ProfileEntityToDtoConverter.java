package com.maxvi.lifeblog.service.conversion;

import com.maxvi.lifeblog.model.ProfileEntity;
import com.maxvi.lifeblog.service.user.dto.ProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class ProfileEntityToDtoConverter implements Converter<ProfileEntity, ProfileDto>
{
    @Nullable
    @Override
    public ProfileDto convert(ProfileEntity profileEntity)
    {
       return null;
    }
}
