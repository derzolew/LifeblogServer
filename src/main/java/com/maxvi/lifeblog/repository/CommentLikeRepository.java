package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long>
{
    CommentLikeEntity findByProfileEntity_Id(Long profileId);
    CommentLikeEntity findByProfileEntity_IdAndCommentEntity_Id(Long profileId, Long commentId);
}
