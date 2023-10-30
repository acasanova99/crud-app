package com.skz.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfigurer {
    @Bean
    public WebMvcConfigurer corsConfigurerBean() {
        System.out.println("CORS configuration....");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:4200",
                                "http://localhost:8080",
                                "http://crud-front:4200",
                                "http://crud-back:8080"
                        ).allowedMethods("GET", "POST","PUT", "DELETE");
            }
        };
    }
}
