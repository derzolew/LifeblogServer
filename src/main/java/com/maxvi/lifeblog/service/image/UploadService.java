package com.maxvi.lifeblog.service.image;

import com.maxvi.lifeblog.service.exception.BadImageSizeException;
import com.maxvi.lifeblog.service.image.dto.ImageDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService
{
    ImageDto saveImage(MultipartFile file) throws IOException, BadImageSizeException, InterruptedException;

    boolean isImage(MultipartFile file);

    Resource getImageAsResource(String fileName);

    Resource getFullImageAsResource(String fileName);
}
