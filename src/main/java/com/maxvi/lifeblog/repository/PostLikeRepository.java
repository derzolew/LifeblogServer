package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long>
{
    PostLikeEntity findAllByBlogPostEntity_Id(Long id);
    PostLikeEntity findByProfileEntityIdAndBlogPostEntity_Id(Long id, Long postId);
}
