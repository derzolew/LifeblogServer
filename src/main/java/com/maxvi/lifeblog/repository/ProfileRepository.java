package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>
{
    ProfileEntity findOneByUserId(Long userId);
    ProfileEntity findByUser_Id(Long userId);
}
