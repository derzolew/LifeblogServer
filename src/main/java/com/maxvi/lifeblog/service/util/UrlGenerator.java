package com.maxvi.lifeblog.service.util;

import com.maxvi.lifeblog.model.ImageEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("urlGenerator")
public class UrlGenerator
{
    @Value("${lifeblog.site.url}")
    private String url;

    public String getUrl(){
        return url;
    }

    public static String getUrlForImage(ImageEntity entity)
    {
        if (entity.getPublicFileName() != null)
        {
            return "/uploads/image/" + entity.getPublicFileName();
        }
        return null;
    }

    public String generatePasswordRestoreLink(String token)
    {
        return url + "/forgotpassword/" + token;
    }
}
