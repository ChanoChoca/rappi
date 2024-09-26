package com.chanochoca.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Cambia esto según el puerto de tu aplicación Angular
                .allowedOrigins("https://localhost:8004")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
