package com.maxvi.lifeblog.controller;

import com.maxvi.lifeblog.service.exception.BadImageSizeException;
import com.maxvi.lifeblog.service.image.UploadService;
import com.maxvi.lifeblog.service.image.dto.ImageDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/uploads")
public class UploadController
{
    @Resource(name = "uploadService")
    private UploadService uploadService;

    @GetMapping(value = "/image/{imageName:.+}", produces = "image/jpeg")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Уменьшенное зображение", response = org.springframework.core.io.Resource.class)
    })
    public ResponseEntity<org.springframework.core.io.Resource> getImage(@ApiParam(value = "Имя изображения", required = true) @PathVariable String imageName)
    {
        org.springframework.core.io.Resource file = uploadService.getImageAsResource(imageName);
        return ResponseEntity.ok(file);
    }

    @GetMapping(value = "/image/original/{imageName:.+}", produces = "image/jpeg")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Оригинальное изображение", response = org.springframework.core.io.Resource.class)
    })
    public ResponseEntity<org.springframework.core.io.Resource> getFullImage(@ApiParam(value = "Имя изображения", required = true) @PathVariable String imageName)
    {
        org.springframework.core.io.Resource file = uploadService.getFullImageAsResource(imageName);
        return ResponseEntity.ok(file);
    }

    @PostMapping(value = "/image", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Информация изображения", response = ImageDto.class),
            @ApiResponse(code = 422, message = "Неверный размер изображения", response = ImageDto.class)
    })
    public ResponseEntity<ImageDto> uploadImage(@ApiParam(value = "Изображение", required = true)  @RequestParam("image") MultipartFile image) throws IOException, BadImageSizeException, InterruptedException
    {
        if (image == null || !uploadService.isImage(image))
        {
            return ResponseEntity.badRequest().build();
        }
        ImageDto result;
        try
        {
            result = uploadService.saveImage(image);
            return ResponseEntity.ok(result);
        } catch (BadImageSizeException e)
        {
            return ResponseEntity.badRequest().build();
        }
    }
}
