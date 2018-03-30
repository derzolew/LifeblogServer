package com.maxvi.lifeblog.service.comment;

import com.maxvi.lifeblog.service.comment.dto.CommentDto;
import com.maxvi.lifeblog.service.comment.dto.CommentLikeDto;
import com.maxvi.lifeblog.service.comment.dto.MakeCommentDto;
import com.maxvi.lifeblog.service.dto.PageDto;

public interface CommentService
{
    PageDto<CommentDto> getCommentsByBlogPostId(Long postId, Integer pageIndex, Integer pageSize);
    CommentDto makeComment(Long postId, MakeCommentDto makeCommentDto);
    CommentLikeDto likeComment(Long postId, Long commentId);
    CommentDto deleteComment(Long postId, Long commentId);
    CommentDto updateComment(Long postId, Long commentId, MakeCommentDto makeCommentDto);
    boolean canProcessComment(Long commentId);
}
