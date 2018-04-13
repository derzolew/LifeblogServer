package com.maxvi.lifeblog.service.image.impl;

import com.maxvi.lifeblog.service.image.ImageService;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service("imageService")
public class ImageServiceImpl implements ImageService
{
    public static final int MIN_IMAGE_HEIGHT = 350;
    public static final int MIN_IMAGE_WIDTH = 500;
    private static final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
    private static final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
    private static final String PNG = "png";

    @Override
    public String createReducedImage(String uploadBaseDirectory, String directory, MultipartFile originalFile, String newFilename, int targetHeight) throws IOException, InterruptedException
    {
        File fileForWrite = Paths.get(uploadBaseDirectory, directory, newFilename).toFile();
        BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(originalFile.getBytes()));
        if (isPng(originalFile))
        {
            sourceImage = bufferedImageForPng(originalFile);
        }

        if (sourceImage.getHeight() < targetHeight)
        {
            if (ImageIO.write(sourceImage, "jpeg", fileForWrite))
            {
                return newFilename;
            }
            else
            {
                throw new IOException("Could not write preview file to " + fileForWrite.toString());
            }
        }
        else
        {
            BufferedImage resizedImage = Scalr.resize(sourceImage, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_HEIGHT, targetHeight);
            if (ImageIO.write(resizedImage, "jpeg", fileForWrite))
            {
                return newFilename;
            }
            else
            {
                throw new IOException("Could not write preview file to " + fileForWrite.toString());
            }
        }
    }

    @Override
    public boolean isImage(MultipartFile inputStreamSource)
    {
        String mimetype = inputStreamSource.getContentType();
        if (!mimetype.contains("/"))
        {
            return false;
        }
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

    @Override
    public boolean saveJpeg(String uploadBaseDirectory, String directory, String file, String newFilename)
    {
        return false;
    }

    @Override
    public boolean checkDimensions(MultipartFile file)
    {
        BufferedImage sourceImage;
        try
        {
            sourceImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            if (sourceImage == null)
            {
                return false;
            }
        }
        catch (IOException e)
        {
            return false;
        }
        return !(sourceImage.getHeight() < MIN_IMAGE_HEIGHT || sourceImage.getWidth() < MIN_IMAGE_WIDTH);
    }

    private boolean isPng(MultipartFile originalFile)
    {
        return originalFile.getContentType().contains(PNG);
    }

    private BufferedImage bufferedImageForPng(MultipartFile originalFile) throws InterruptedException, IOException
    {
        Image img = Toolkit.getDefaultToolkit().createImage(originalFile.getBytes());
        PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, true);
        pg.grabPixels();
        int width = pg.getWidth(), height = pg.getHeight();
        DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
        WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
        return new BufferedImage(RGB_OPAQUE, raster, false, null);
    }
}
