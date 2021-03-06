package com.maxvi.lifeblog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "profile", schema = "public")
public class ProfileEntity implements Serializable
{
    private Long id;
    private UserEntity user;
    private Date birthday;
    private String description;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private ImageEntity photo;
    private List<BlogPostEntity> blogPostEntities;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity getUser()
    {
        return user;
    }

    public void setUser(UserEntity user)
    {
        this.user = user;
    }

    @Column(name = "birthday")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    @Column(name = "description")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Column(name = "first_name")
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<BlogPostEntity> getBlogPostEntities()
    {
        return blogPostEntities;
    }

    public void setBlogPostEntities(List<BlogPostEntity> blogPostEntities)
    {
        this.blogPostEntities = blogPostEntities;
    }

    @ManyToOne(targetEntity = ImageEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_photo_id")
    public ImageEntity getPhoto()
    {
        return photo;
    }

    public void setPhoto(ImageEntity photo)
    {
        this.photo = photo;
    }
}
