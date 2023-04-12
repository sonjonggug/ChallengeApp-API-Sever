//package com.upside.api.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//	
//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")
////                .allowedOrigins("*")
//        		.allowedOrigins("http://localhost:3000","http://localhost:8080","http://localhost:9999") // 두개 Origin(출처) 허용
//                .allowedMethods("*") // 모든 메소드타입 ( GET , POST 등.. ) 허용
//                .allowedHeaders("*"); // 모든 헤더 허용
//    }
//}