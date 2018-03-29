package com.maxvi.lifeblog.service.comment.impl;

import com.maxvi.lifeblog.model.BlogPostEntity;
import com.maxvi.lifeblog.model.CommentEntity;
import com.maxvi.lifeblog.model.CommentLikeEntity;
import com.maxvi.lifeblog.repository.BlogRepository;
import com.maxvi.lifeblog.repository.CommentLikeRepository;
import com.maxvi.lifeblog.repository.CommentRepository;
import com.maxvi.lifeblog.service.comment.CommentService;
import com.maxvi.lifeblog.service.comment.dto.CommentDto;
import com.maxvi.lifeblog.service.comment.dto.CommentLikeDto;
import com.maxvi.lifeblog.service.comment.dto.MakeCommentDto;
import com.maxvi.lifeblog.service.conversion.Converters;
import com.maxvi.lifeblog.service.dto.PageDto;
import com.maxvi.lifeblog.service.user.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("commentService")
public class CommentServiceImpl implements CommentService
{
    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

    @Resource(name = "profileService")
    private ProfileService profileService;

    @Resource(name = "commentLikeRepository")
    private CommentLikeRepository commentLikeRepository;

    @Resource(name = "blogRepository")
    private BlogRepository blogRepository;

    @Override
    public PageDto<CommentDto> getCommentsByBlogPostId(Long postId, Integer pageIndex, Integer pageSize)
    {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Page<CommentEntity> commentEntities = commentRepository.findAllByBlogPostEntity_Id(postId, pageRequest);
        List<CommentDto> commentDtoList = commentEntities
                .getContent()
                .stream()
                .map(Converters::convertCommentEntityToDto)
                .collect(Collectors.toList());
        return new PageDto<>(commentDtoList, commentEntities.getTotalElements(), commentEntities.getTotalPages());
    }

    @Override
    @Transactional
    public CommentDto makeComment(Long postId, MakeCommentDto makeCommentDto)
    {
        BlogPostEntity blogPostEntity = blogRepository.findById(postId).orElse(null);

        if (blogPostEntity != null)
        {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setComment(makeCommentDto.getComment());
            commentEntity.setDate(new Date());
            commentEntity.setBlogPostEntity(blogPostEntity);
            commentEntity.setCommentLikeEntities(new ArrayList<>());
            commentEntity.setProfileEntity(profileService.getCurrentUserProfile());
            commentEntity = commentRepository.save(commentEntity);
            return Converters.convertCommentEntityToDto(commentEntity);
        }

        return null;
    }

    @Override
    @Transactional
    public CommentLikeDto likeComment(Long postId, Long commentId)
    {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        if (commentEntity != null)
        {
            CommentLikeEntity commentLikeEntity = commentLikeRepository.findByProfileEntity_Id(profileService.getCurrentUserProfile().getId());
            if (commentLikeEntity == null)
            {
                commentLikeEntity = new CommentLikeEntity();
                commentLikeEntity.setCommentEntity(commentEntity);
                commentLikeEntity.setDate(new Date());
                commentLikeEntity.setProfileEntity(profileService.getCurrentUserProfile());
                return Converters.convertCommentLikeEntityToDto(commentLikeEntity);
            }
            else
            {
                commentLikeRepository.delete(commentLikeEntity);
            }

        }
        return null;
    }

    @Override
    @Transactional
    public CommentDto deleteComment(Long postId, Long commentId)
    {
        return null;
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long postId, Long commentId, MakeCommentDto makeCommentDto)
    {
        return null;
    }
}
