package com.maxvi.lifeblog.service.blog;

import com.maxvi.lifeblog.service.dto.PageDto;
import com.maxvi.lifeblog.service.blog.dto.PostDto;

public interface BlogService
{
    PageDto<PostDto> getBlogPostsByProfileId(Long profileId, Integer pageIndex, Integer size);
}
