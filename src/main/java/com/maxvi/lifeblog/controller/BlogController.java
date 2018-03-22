package com.maxvi.lifeblog.controller;

import com.maxvi.lifeblog.service.blog.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/post")
public class BlogController
{
    @Resource(name = "blogService")
    private BlogService blogService;

    @GetMapping("/all/{profileId}")
    public ResponseEntity getPostsByProfileId(@PathVariable("profileId") Long profileId,
                                              @RequestParam("page") Integer pageIndex,
                                              @RequestParam("size") Integer size)
    {
        return ResponseEntity.ok(blogService.getBlogPostsByProfileId(profileId, pageIndex, size));
    }
}
