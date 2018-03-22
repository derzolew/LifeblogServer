package com.maxvi.lifeblog.service.blog.impl;

import com.maxvi.lifeblog.model.BlogPostEntity;
import com.maxvi.lifeblog.repository.BlogRepository;
import com.maxvi.lifeblog.service.blog.BlogService;
import com.maxvi.lifeblog.service.blog.dto.PostDto;
import com.maxvi.lifeblog.service.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("blogService")
public class BlogServiceImpl implements BlogService
{
    @Resource(name = "blogRepository")
    private BlogRepository blogRepository;

    @Override
    public PageDto<PostDto> getBlogPostsByProfileId(Long profileId, Integer pageIndex, Integer size)
    {
        PageRequest pageRequest = PageRequest.of(pageIndex, size);
        Page<BlogPostEntity> blogPostEntities = blogRepository.findAllByProfileId(profileId, pageRequest);
        List<PostDto> postDtoList = blogPostEntities.getContent().stream().map(this::postEntityToDto).collect(Collectors.toList());
        return new PageDto<>(postDtoList, blogPostEntities.getTotalElements(), blogPostEntities.getTotalPages());
    }

    private PostDto postEntityToDto(BlogPostEntity blogPostEntity)
    {
        PostDto postDto = new PostDto();
        postDto.setDate(blogPostEntity.getDate());
        postDto.setId(blogPostEntity.getId());
        postDto.setPost(blogPostEntity.getPost());
        return postDto;
    }

}
