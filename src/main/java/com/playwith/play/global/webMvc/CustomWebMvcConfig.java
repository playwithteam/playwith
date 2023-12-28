package com.playwith.play.global.webMvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    @Value("${custom.genFile.dirPath}")
    private String getGenFileDirPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(getGenFileDirPath);
        registry.addResourceHandler("/gen/**")
                .addResourceLocations("file:///" + getGenFileDirPath + "/");
    }
}