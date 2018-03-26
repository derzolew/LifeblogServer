package com.maxvi.lifeblog.model;

import com.maxvi.lifeblog.model.profile.ProfileEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blog_post", schema = "public")
public class BlogPostEntity implements Serializable
{
    private Long id;
    private String post;
    private Date date;
    private ProfileEntity profile;
    private List<PostLikeEntity> likes;
    private List<CommentEntity> comments;

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

    @Column(name = "post")
    public String getPost()
    {
        return post;
    }

    public void setPost(String post)
    {
        this.post = post;
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

    @ManyToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    public ProfileEntity getProfile()
    {
        return profile;
    }

    public void setProfile(ProfileEntity profile)
    {
        this.profile = profile;
    }

    @OneToMany(mappedBy = "blogPostEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<PostLikeEntity> getLikes()
    {
        return likes;
    }

    public void setLikes(List<PostLikeEntity> likes)
    {
        this.likes = likes;
    }

    @OneToMany(mappedBy = "blogPostEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<CommentEntity> getComments()
    {
        return comments;
    }

    public void setComments(List<CommentEntity> comments)
    {
        this.comments = comments;
    }
}
