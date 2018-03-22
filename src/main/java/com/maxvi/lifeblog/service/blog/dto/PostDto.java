package com.maxvi.lifeblog.service.blog.dto;

import java.util.Date;

public class PostDto
{
    private Long id;
    private String post;
    private Date date;

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

    public void setDate(Date date)
    {
        this.date = date;
    }
}
