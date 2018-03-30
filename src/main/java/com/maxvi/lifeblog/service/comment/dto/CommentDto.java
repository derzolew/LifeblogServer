package com.maxvi.lifeblog.service.comment.dto;

import com.maxvi.lifeblog.service.user.dto.ProfileDto;

import java.util.Date;
import java.util.List;

public class CommentDto
{
    private Long id;
    private Date date;
    private String comment;
    private ProfileDto profile;
    private List<CommentLikeDto> commentLikes;

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

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public ProfileDto getProfile()
    {
        return profile;
    }

    public void setProfile(ProfileDto profile)
    {
        this.profile = profile;
    }

    public List<CommentLikeDto> getCommentLikes()
    {
        return commentLikes;
    }

    public void setCommentLikes(List<CommentLikeDto> commentLikes)
    {
        this.commentLikes = commentLikes;
    }
}
