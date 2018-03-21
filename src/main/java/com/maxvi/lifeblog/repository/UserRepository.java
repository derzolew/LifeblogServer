package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByLogin(String login);
}
