package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.BlogPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogRepository extends JpaRepository<BlogPostEntity, Long>
{
    Page<BlogPostEntity> findAllByProfileId(Long profileId, Pageable pageable);

}
