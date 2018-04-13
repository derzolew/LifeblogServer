package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.ImageEntity;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<ImageEntity, Long>
{
    ImageEntity findImageEntityByPublicFileName(String publicFileName);
}
