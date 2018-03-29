package com.maxvi.lifeblog.service.comment.dto;

import com.maxvi.lifeblog.service.dto.ProfileDto;

import java.util.Date;

public class CommentLikeDto
{
    private Long id;
    private Date date;
    private ProfileDto profile;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public ProfileDto getProfile()
    {
        return profile;
    }

    public void setProfile(ProfileDto profile)
    {
        this.profile = profile;
    }
}
