package com.maxvi.lifeblog.service.blog.dto;

import com.maxvi.lifeblog.service.dto.ProfileDto;

import java.util.Date;

public class PostLikeDto
{
    private Long id;
    private ProfileDto profileDto;
    private Date date;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ProfileDto getProfileDto()
    {
        return profileDto;
    }

    public void setProfileDto(ProfileDto profileDto)
    {
        this.profileDto = profileDto;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
