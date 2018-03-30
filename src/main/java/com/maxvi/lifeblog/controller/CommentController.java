package com.maxvi.lifeblog.controller;

import com.maxvi.lifeblog.service.comment.CommentService;
import com.maxvi.lifeblog.service.comment.dto.CommentDto;
import com.maxvi.lifeblog.service.comment.dto.CommentLikeDto;
import com.maxvi.lifeblog.service.comment.dto.MakeCommentDto;
import com.maxvi.lifeblog.service.dto.PageDto;
import com.maxvi.lifeblog.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/post")
public class CommentController
{
    @Resource(name = "commentService")
    private CommentService commentService;

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/{postId}/comments")
    public ResponseEntity<PageDto<CommentDto>> getCommentByBlogPostId(@PathVariable("postId") Long postId,
                                                                      @RequestParam("page") Integer pageIndex,
                                                                      @RequestParam("size") Integer pageSize)
    {
        return ResponseEntity.ok(commentService.getCommentsByBlogPostId(postId, pageIndex, pageSize));
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<CommentDto> makeCommentByPostId(@PathVariable("postId") Long postId,
                                                          @RequestBody @Valid MakeCommentDto makeCommentDto)
    {
        boolean canMakeComment = userService.isCurrentUserOrAdmin(userService.getCurrentUser().getId());
        if (canMakeComment)
        {
            CommentDto commentDto = commentService.makeComment(postId, makeCommentDto);
            return ResponseEntity.ok(commentDto);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentLikeDto> likeComment(@PathVariable("postId") Long postId,
                                                      @PathVariable("commentId") Long commentId)
    {
        boolean canLikeComment = userService.isCurrentUserOrAdmin(userService.getCurrentUser().getId());
        if (canLikeComment)
        {
            CommentLikeDto commentLikeDto = commentService.likeComment(postId, commentId);
            return ResponseEntity.ok(commentLikeDto);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable("postId") Long postId,
                                                    @PathVariable("commentId") Long commentId)
    {
        boolean canDeleteComment = commentService.canProcessComment(commentId);
        if (canDeleteComment)
        {
            CommentDto commentDto = commentService.deleteComment(postId, commentId);
            return ResponseEntity.ok(commentDto);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable("postId") Long postId,
                                                  @PathVariable("commentId") Long commentId,
                                                  @RequestBody @Valid MakeCommentDto makeCommentDto)
    {
        boolean canEditComment = commentService.canProcessComment(commentId);
        if (canEditComment)
        {
            CommentDto commentDto = commentService.updateComment(postId, commentId, makeCommentDto);
            return ResponseEntity.ok(commentDto);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
