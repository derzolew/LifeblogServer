package com.maxvi.lifeblog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comment-like", schema = "public")
public class CommentLikeEntity implements Serializable
{
    private Long id;
    private CommentEntity commentEntity;
    private Date date;
    private ProfileEntity profileEntity;

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

    @ManyToOne(targetEntity = CommentEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    public CommentEntity getCommentEntity()
    {
        return commentEntity;
    }

    public void setCommentEntity(CommentEntity commentEntity)
    {
        this.commentEntity = commentEntity;
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
}
