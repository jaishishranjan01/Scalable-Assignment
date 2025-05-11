package com.etrain.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") //Allow all paths
                        .allowedOrigins("http://localhost:3000", "http://localhost:30080") //Allow React frontend
                        .allowedMethods("*") //Allow all methods: GET, POST, PUT, DELETE, OPTIONS
                        .allowedHeaders("*") //Allow any headers
                        .allowCredentials(true); //Allow cookies/token
            }
        };
    }
}
