package com.maxvi.lifeblog.service.image.impl;

import com.maxvi.lifeblog.model.ImageEntity;
import com.maxvi.lifeblog.model.StorageEntity;
import com.maxvi.lifeblog.model.UserEntity;
import com.maxvi.lifeblog.repository.ImageRepository;
import com.maxvi.lifeblog.repository.StorageRepository;
import com.maxvi.lifeblog.service.conversion.Converters;
import com.maxvi.lifeblog.service.exception.BadImageSizeException;
import com.maxvi.lifeblog.service.image.ImageService;
import com.maxvi.lifeblog.service.image.UploadService;
import com.maxvi.lifeblog.service.image.dto.ImageDto;
import com.maxvi.lifeblog.service.security.SecurityService;
import com.maxvi.lifeblog.service.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("uploadService")
public class UploadServiceImpl implements UploadService
{
    private static final int PUBLIC_IMAGE_NAME_LENGTH = 12;
    private static final String ORIGINAL_FILE_NAME = "orig.jpg";
    private static final String REDUCED_FILE_NAME = "reduced.jpg";
    private static final int REDUCED_FILE_HEIGHT = 900;
    public static final int ORIGINAL_FILE_HEIGHT = 1500;

    @Value("${lifeblog.upload.directory}")
    private String uploadDirectoryPath;

    @javax.annotation.Resource(name = "imageService")
    private ImageService imageService;
    @javax.annotation.Resource(name = "storageRepository")
    private StorageRepository storageRepository;
    @javax.annotation.Resource(name = "imageRepository")
    private ImageRepository imageRepository;
    @javax.annotation.Resource(name = "conversionService")
    private ConversionService conversionService;
    @javax.annotation.Resource(name = "securityService")
    private SecurityService securityService;

    @PostConstruct
    public void init()
    {
        Path uploadPath = Paths.get(uploadDirectoryPath);
        if (!Files.exists(uploadPath) || !Files.isDirectory(uploadPath))
        {
            if (!(new File(uploadDirectoryPath).mkdirs()))
            {
            }

        }
    }

    @Override
    public ImageDto saveImage(MultipartFile file) throws IOException, BadImageSizeException, InterruptedException
    {
        Path uploadPath = Paths.get(uploadDirectoryPath);
        if (Files.exists(uploadPath) && Files.isDirectory(uploadPath) && Files.isWritable(uploadPath))
        {
            if (!imageService.checkDimensions(file))
            {
                throw new BadImageSizeException("Size of this image is not valid!");
            }
            String directory = generateDateFileName();
            String fileFolder = generateFileName(11);
            Path targetDirectory = Paths.get(uploadDirectoryPath, directory, fileFolder);
            if (!(new File(targetDirectory.toUri()).mkdirs()))
            {
                throw new IOException("Upload directory is not writable!");
            }
            String originalFileName = imageService.createReducedImage(uploadDirectoryPath, Paths.get(directory, fileFolder).toString(), file, ORIGINAL_FILE_NAME, ORIGINAL_FILE_HEIGHT);
            String reducedFileName = imageService.createReducedImage(uploadDirectoryPath, Paths.get(directory, fileFolder).toString(), file, REDUCED_FILE_NAME, REDUCED_FILE_HEIGHT);
            ImageEntity entity = saveImageInformation(Paths.get(directory, fileFolder).toString(), originalFileName, reducedFileName);
            return Converters.imageEntityToDtoConverter(entity);
        }
        else
        {
            throw new IOException("Upload directory is not writable!");
        }
    }

    @Override
    public boolean isImage(MultipartFile file)
    {
        return imageService.isImage(file);
    }

    @Override
    public Resource getImageAsResource(String fileName)
    {
        ImageEntity imageEntity = imageRepository.findImageEntityByPublicFileName(fileName);
        File imageFile = Paths.get(imageEntity.getStorage().getPath(), imageEntity.getDirectory(), imageEntity.getReducedFileName()).toFile();
        return new FileSystemResource(imageFile);
    }

    @Override
    public Resource getFullImageAsResource(String fileName)
    {
        ImageEntity imageEntity = imageRepository.findImageEntityByPublicFileName(fileName);
        File imageFile = Paths.get(imageEntity.getStorage().getPath(), imageEntity.getDirectory(), imageEntity.getOriginalFileName()).toFile();
        return new FileSystemResource(imageFile);
    }

    private ImageEntity saveImageInformation(String directory, String originalFileName, String reducedFileName)
    {
        ImageEntity imageEntity = new ImageEntity();
        StorageEntity storage = getOrCreateCurrentStorage();
        imageEntity.setStorage(storage);
        imageEntity.setDirectory(directory);
        imageEntity.setOriginalFileName(originalFileName);
        imageEntity.setReducedFileName(reducedFileName);
        imageEntity.setPublicFileName(generateFileName(PUBLIC_IMAGE_NAME_LENGTH) + ".jpg");
        UserEntity currentUserEntity = securityService.getCurrentUserEntity();
        imageEntity.setUploader(currentUserEntity);
        return imageRepository.save(imageEntity);
    }

    private StorageEntity getOrCreateCurrentStorage()
    {
        StorageEntity currentStorage = storageRepository.findStorageEntityByPath(uploadDirectoryPath);
        if (currentStorage == null)
        {
            StorageEntity newStorage = new StorageEntity();
            newStorage.setPath(uploadDirectoryPath);
            return storageRepository.save(newStorage);
        }
        return currentStorage;
    }

    private String generateFileName(int length)
    {
        RandomStringGenerator stringGenerator = new RandomStringGenerator
                .RandomStringGeneratorBuilder()
                .useLower(true)
                .useUpper(true)
                .useDigits(true)
                .build();
        return stringGenerator.generate(length);
    }

    private String generateDateFileName()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }
}
