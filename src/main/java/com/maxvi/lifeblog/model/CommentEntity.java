package com.maxvi.lifeblog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment", schema = "public")
public class CommentEntity implements Serializable
{
    private Long id;
    private BlogPostEntity postEntity;
    private Date date;
    private String comment;
    private ProfileEntity profileEntity;
    private List<CommentLikeEntity> commentLikeEntities;

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

    @ManyToOne(targetEntity = BlogPostEntity.class)
    @JoinColumn(name = "post_id")
    public BlogPostEntity getBlogPostEntity()
    {
        return postEntity;
    }

    public void setBlogPostEntity(BlogPostEntity blogPostEntity)
    {
        this.postEntity = blogPostEntity;
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

    @Column(name = "comment")
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @OneToOne(targetEntity = ProfileEntity.class)
    @JoinColumn(name = "profile_id")
    public ProfileEntity getProfileEntity()
    {
        return profileEntity;
    }

    public void setProfileEntity(ProfileEntity profileEntity)
    {
        this.profileEntity = profileEntity;
    }

    @OneToMany(mappedBy = "commentEntity", cascade = CascadeType.ALL)
    public List<CommentLikeEntity> getCommentLikeEntities()
    {
        return commentLikeEntities;
    }

    public void setCommentLikeEntities(List<CommentLikeEntity> commentLikeEntities)
    {
        this.commentLikeEntities = commentLikeEntities;
    }
}
