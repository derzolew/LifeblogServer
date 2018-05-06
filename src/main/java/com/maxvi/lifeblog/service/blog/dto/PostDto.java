package com.maxvi.lifeblog.service.blog.dto;

import com.maxvi.lifeblog.service.user.dto.ProfileDto;

import java.util.Date;

public class PostDto
{
    private Long id;
    private String post;
    private Date date;
    private String title;
    private String photoName;
    private String photoUrl;
    private ProfileDto profileDto;

    public ProfileDto getProfileDto()
    {
        return profileDto;
    }

    public void setProfileDto(ProfileDto profileDto)
    {
        this.profileDto = profileDto;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPost()
    {
        return post;
    }

    public void setPost(String post)
    {
        this.post = post;
    }

    public Date getDate()
    {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
