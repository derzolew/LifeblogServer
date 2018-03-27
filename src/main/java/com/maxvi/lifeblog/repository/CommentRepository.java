package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>
{
}
