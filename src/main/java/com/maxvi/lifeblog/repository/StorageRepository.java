package com.maxvi.lifeblog.repository;

import com.maxvi.lifeblog.model.StorageEntity;
import org.springframework.data.repository.CrudRepository;

public interface StorageRepository extends CrudRepository<StorageEntity, Long>
{
    StorageEntity findStorageEntityByPath(String path);
}
