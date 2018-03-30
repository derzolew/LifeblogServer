package com.maxvi.lifeblog.service.blog;

import com.maxvi.lifeblog.service.blog.dto.PostLikeDto;
import com.maxvi.lifeblog.service.exception.BlogPostNotFoundException;
import com.maxvi.lifeblog.service.dto.PageDto;
import com.maxvi.lifeblog.service.blog.dto.PostDto;

public interface BlogService
{
    PageDto<PostDto> getBlogPostsByProfileId(Long profileId, Integer pageIndex, Integer size);
    PageDto<PostDto> getAllBlogPosts(Integer pageIndex, Integer size);
    PostDto getPostById(Long postId);
    PostDto makePost(PostDto postDto);
    PostDto deletePost(Long postId) throws BlogPostNotFoundException;
    PostDto updatePost(Long postId, PostDto postDto) throws BlogPostNotFoundException;
    PostLikeDto likePost(Long postId);
}
