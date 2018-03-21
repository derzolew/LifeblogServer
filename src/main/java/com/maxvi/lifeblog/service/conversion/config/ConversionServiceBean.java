package com.maxvi.lifeblog.service.conversion.config;

import com.maxvi.lifeblog.service.conversion.ProfileEntityToDtoConverter;
import com.maxvi.lifeblog.service.conversion.UserEntityToCurrentUserDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionServiceBean
{
    @Bean
    public ConversionServiceFactoryBean conversionService()
    {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new UserEntityToCurrentUserDtoConverter());
        converters.add(new ProfileEntityToDtoConverter());
        conversionServiceFactoryBean.setConverters(converters);
        return conversionServiceFactoryBean;
    }
}
