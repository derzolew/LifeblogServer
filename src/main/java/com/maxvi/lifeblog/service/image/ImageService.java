package com.maxvi.lifeblog.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService
{
    String createReducedImage(String uploadBaseDirectory, String directory, MultipartFile originalFile, String newFilename, int targetHeight) throws IOException, InterruptedException;

    boolean isImage(MultipartFile inputStreamSource);

    boolean saveJpeg(String uploadBaseDirectory, String directory, String file, String newFilename);

    boolean checkDimensions(MultipartFile file);
}
