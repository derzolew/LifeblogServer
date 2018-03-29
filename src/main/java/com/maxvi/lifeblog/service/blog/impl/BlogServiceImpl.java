package com.maxvi.lifeblog.service.blog.impl;

import com.maxvi.lifeblog.model.BlogPostEntity;
import com.maxvi.lifeblog.model.PostLikeEntity;
import com.maxvi.lifeblog.repository.BlogRepository;
import com.maxvi.lifeblog.repository.PostLikeRepository;
import com.maxvi.lifeblog.service.blog.BlogService;
import com.maxvi.lifeblog.service.blog.dto.PostDto;
import com.maxvi.lifeblog.service.blog.dto.PostLikeDto;
import com.maxvi.lifeblog.service.blog.exception.BlogPostNotFoundException;
import com.maxvi.lifeblog.service.conversion.Converters;
import com.maxvi.lifeblog.service.dto.PageDto;
import com.maxvi.lifeblog.service.dto.ProfileDto;
import com.maxvi.lifeblog.service.user.ProfileService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("blogService")
public class BlogServiceImpl implements BlogService
{
    private static final String BLOG_POST_NOT_FOUND = "Blog post could not be found";

    @Resource(name = "blogRepository")
    private BlogRepository blogRepository;

    @Resource(name = "profileService")
    private ProfileService profileService;

    @Resource(name = "conversionService")
    private ConversionService conversionService;

    @Resource(name = "postLikeRepository")
    private PostLikeRepository postLikeRepository;

    @Override
    public PageDto<PostDto> getBlogPostsByProfileId(Long profileId, Integer pageIndex, Integer size)
    {
        PageRequest pageRequest = PageRequest.of(pageIndex, size);
        Page<BlogPostEntity> blogPostEntities = blogRepository.findAllByProfileId(profileId, pageRequest);
        List<PostDto> postDtoList = blogPostEntities.getContent().stream().map(this::postEntityToDto).collect(Collectors.toList());
        return new PageDto<>(postDtoList, blogPostEntities.getTotalElements(), blogPostEntities.getTotalPages());
    }

    @Override
    public PostDto getPostById(Long postId)
    {
        BlogPostEntity blogPostEntity = blogRepository.getOne(postId);
        return postEntityToDto(blogPostEntity);
    }

    @Override
    @Transactional
    public PostDto makePost(PostDto postDto)
    {
        BlogPostEntity blogPostEntity = blogRepository.save(setPropertiesToEntity(postDto));
        return postEntityToDto(blogPostEntity);
    }

    @Override
    @Transactional
    public PostLikeDto likePost(Long postId)
    {
        BlogPostEntity blogPostEntity = blogRepository.findById(postId).orElse(null);

        PostLikeEntity postLikeEntity = postLikeRepository.findByProfileEntityId(profileService.getCurrentUserProfile().getId());
        if (postLikeEntity == null)
        {
            postLikeEntity = new PostLikeEntity();
            postLikeEntity.setBlogPostEntity(blogPostEntity);
            postLikeEntity.setDate(new Date());
            postLikeEntity.setProfileEntity(profileService.getCurrentUserProfile());
            postLikeEntity = postLikeRepository.save(postLikeEntity);
            return postLikeEntityToDto(postLikeEntity);
        }
        postLikeRepository.delete(postLikeEntity);
        return postLikeEntityToDto(postLikeEntity);
    }

    @Override
    @Transactional
    public PostDto deletePost(Long postId) throws BlogPostNotFoundException
    {
        BlogPostEntity blogPostEntity = blogRepository.findById(postId).orElse(null);
        if (blogPostEntity == null)
        {
            throw new BlogPostNotFoundException(BLOG_POST_NOT_FOUND);
        }
        PostDto postDto = postEntityToDto(blogPostEntity);
        blogRepository.delete(blogPostEntity);
        return postDto;
    }

    @Override
    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto) throws BlogPostNotFoundException
    {
        BlogPostEntity blogPostEntity = blogRepository.findById(postId).orElse(null);

        if (blogPostEntity == null)
        {
            throw new BlogPostNotFoundException(BLOG_POST_NOT_FOUND);
        }
        updateBlogPostEntity(blogPostEntity, postDto);
        blogPostEntity = blogRepository.save(blogPostEntity);
        return postEntityToDto(blogPostEntity);
    }

    @Override
    public PageDto<PostDto> getAllBlogPosts(Integer pageIndex, Integer size)
    {
        PageRequest pageRequest = PageRequest.of(pageIndex, size);
        Page<BlogPostEntity> blogPostEntities = blogRepository.findAll(pageRequest);
        List<PostDto> postDtoList = blogPostEntities.getContent().stream().map(this::postEntityToDto).collect(Collectors.toList());
        return new PageDto<>(postDtoList, blogPostEntities.getTotalElements(), blogPostEntities.getTotalPages());
    }

    private PostLikeDto postLikeEntityToDto(PostLikeEntity postLikeEntity)
    {
        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setDate(postLikeEntity.getDate());
        postLikeDto.setId(postLikeEntity.getId());

        ProfileDto profileDto = conversionService.convert(postLikeEntity.getProfileEntity(), ProfileDto.class);

        postLikeDto.setProfileDto(profileDto);
        return postLikeDto;
    }

    private BlogPostEntity setPropertiesToEntity(PostDto postDto) {
        BlogPostEntity blogPostEntity = new BlogPostEntity();
        blogPostEntity.setPost(postDto.getPost());
        blogPostEntity.setDate(postDto.getDate());
        blogPostEntity.setComments(new ArrayList<>());
        blogPostEntity.setLikes(new ArrayList<>());
        blogPostEntity.setProfile(profileService.getCurrentUserProfile());
        return blogPostEntity;
    }

    private void updateBlogPostEntity(BlogPostEntity postEntity, PostDto postDto)
    {
        postEntity.setDate(postDto.getDate());
        postEntity.setPost(postDto.getPost());
    }

    private PostDto postEntityToDto(BlogPostEntity blogPostEntity)
    {
        PostDto postDto = new PostDto();
        postDto.setDate(blogPostEntity.getDate());
        postDto.setId(blogPostEntity.getId());
        postDto.setPost(blogPostEntity.getPost());

        ProfileDto profileDto = Converters.profileEntityToDtoConverter(blogPostEntity.getProfile());
        postDto.setProfileDto(profileDto);
        return postDto;
    }


}
