package com.maxvi.lifeblog.service.conversion;

import com.maxvi.lifeblog.model.CommentEntity;
import com.maxvi.lifeblog.model.CommentLikeEntity;
import com.maxvi.lifeblog.model.ProfileEntity;
import com.maxvi.lifeblog.service.comment.dto.CommentDto;
import com.maxvi.lifeblog.service.comment.dto.CommentLikeDto;
import com.maxvi.lifeblog.service.dto.ProfileDto;

import java.util.List;
import java.util.stream.Collectors;

public class Converters
{

    public static CommentDto convertCommentEntityToDto(CommentEntity commentEntity)
    {
        CommentDto commentDto = new CommentDto();
        commentDto.setComment(commentEntity.getComment());
        commentDto.setDate(commentEntity.getDate());
        commentDto.setId(commentEntity.getId());
        commentDto.setProfile(profileEntityToDtoConverter(commentEntity.getProfileEntity()));

        List<CommentLikeDto> commentLikeDtoList = commentEntity
                .getCommentLikeEntities()
                .stream()
                .map(Converters::convertCommentLikeEntityToDto)
                .collect(Collectors.toList());

        commentDto.setCommentLikes(commentLikeDtoList);
        return commentDto;
    }

    public static CommentLikeDto convertCommentLikeEntityToDto(CommentLikeEntity commentLikeEntity)
    {
        CommentLikeDto commentLikeDto = new CommentLikeDto();
        commentLikeDto.setProfile(profileEntityToDtoConverter(commentLikeEntity.getProfileEntity()));
        commentLikeDto.setId(commentLikeEntity.getId());
        commentLikeDto.setDate(commentLikeEntity.getDate());
        return commentLikeDto;
    }

    public static ProfileDto profileEntityToDtoConverter(ProfileEntity profileEntity)
    {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profileEntity.getId());
        profileDto.setBirthday(profileEntity.getBirthday());
        profileDto.setDescription(profileEntity.getDescription());
        profileDto.setFirstName(profileEntity.getFirstName());
        profileDto.setLastName(profileEntity.getLastName());
        profileDto.setPhoneNumber(profileEntity.getPhoneNumber());
        return profileDto;
    }
}
