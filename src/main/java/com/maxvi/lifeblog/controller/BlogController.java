package com.maxvi.lifeblog.controller;

import com.maxvi.lifeblog.service.blog.BlogService;
import com.maxvi.lifeblog.service.blog.dto.PostDto;
import com.maxvi.lifeblog.service.blog.dto.PostLikeDto;
import com.maxvi.lifeblog.service.exception.BlogPostNotFoundException;
import com.maxvi.lifeblog.service.dto.PageDto;
import com.maxvi.lifeblog.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/post")
public class BlogController
{
    @Resource(name = "blogService")
    private BlogService blogService;

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/all/{profileId}")
    public ResponseEntity<PageDto<PostDto>> getPostsByProfileId(@PathVariable("profileId") Long profileId,
                                                                @RequestParam("page") Integer pageIndex,
                                                                @RequestParam("size") Integer size)
    {
        return ResponseEntity.ok(blogService.getBlogPostsByProfileId(profileId, pageIndex, size));
    }

    @GetMapping("/all")
    public ResponseEntity<PageDto<PostDto>> getAllPosts(@RequestParam("page") Integer pageIndex,
                                                        @RequestParam("size") Integer size)
    {
        return ResponseEntity.ok(blogService.getAllBlogPosts(pageIndex, size));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long postId)
    {
        return ResponseEntity.ok(blogService.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<PostDto> makePost(@RequestBody PostDto postDto)
    {
        return ResponseEntity.ok(blogService.makePost(postDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDto> deletePost(@PathVariable("postId") Long postId)
    {
        boolean canDeletePost = userService.isCurrentUserOrAdmin(userService.getCurrentUser().getId());
        if (canDeletePost)
        {
            try
            {
                PostDto deletedPost = blogService.deletePost(postId);
                return ResponseEntity.ok(deletedPost);
            }
            catch (BlogPostNotFoundException e)
            {
                return ResponseEntity.badRequest().build();
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostLikeDto> likePost(@PathVariable("postId") Long postId)
    {
        return ResponseEntity.ok(blogService.likePost(postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Long postId,
                                              @RequestBody @Valid PostDto postDto)
    {
        boolean canUpdatePost = userService.isCurrentUserOrAdmin(userService.getCurrentUser().getId());
        if (canUpdatePost)
        {
            try
            {
                PostDto updatedPost = blogService.updatePost(postId, postDto);
                return ResponseEntity.ok(updatedPost);
            } catch (BlogPostNotFoundException e)
            {
                return ResponseEntity.badRequest().build();
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
