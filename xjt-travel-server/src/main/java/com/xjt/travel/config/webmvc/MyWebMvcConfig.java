package com.xjt.travel.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/travelFiles/**")
                .addResourceLocations(
                        "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"travelFiles"+ System.getProperty("file.separator")
                );
    }
}