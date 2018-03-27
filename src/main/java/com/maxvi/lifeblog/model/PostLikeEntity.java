package com.maxvi.lifeblog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "post_like", schema = "public")
public class PostLikeEntity implements Serializable
{
    private Long id;
    private BlogPostEntity blogPostEntity;
    private ProfileEntity profileEntity;
    private Date date;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @ManyToOne(targetEntity = BlogPostEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    public BlogPostEntity getBlogPostEntity()
    {
        return blogPostEntity;
    }

    public void setBlogPostEntity(BlogPostEntity blogPostEntity)
    {
        this.blogPostEntity = blogPostEntity;
    }

    @OneToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    public ProfileEntity getProfileEntity()
    {
        return profileEntity;
    }

    public void setProfileEntity(ProfileEntity profileEntity)
    {
        this.profileEntity = profileEntity;
    }

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
