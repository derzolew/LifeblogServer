package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long>
{
}
