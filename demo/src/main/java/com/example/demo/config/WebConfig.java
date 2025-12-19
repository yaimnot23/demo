package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map /image/** URL to the physical file system path
        // "file:///" + System.getProperty("user.dir") + "/src/main/resources/static/image/"
        String resourcePath = "file:///" + System.getProperty("user.dir") + "/src/main/resources/static/image/";
        
        registry.addResourceHandler("/image/**")
                .addResourceLocations(resourcePath);
    }
}
