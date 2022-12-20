package com.its.ex.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**";     // html(뷰단)에서 사용할 경로
    private String filePath = "file:///D:/boot_final_file/";        // 실제 파일 저장 경로
    private String profilePath = "file:///D:/boot_final_profile/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(filePath);
//        registry.addResourceHandler(resourcePath).addResourceLocations(filePath, profilePath);
    }
}
