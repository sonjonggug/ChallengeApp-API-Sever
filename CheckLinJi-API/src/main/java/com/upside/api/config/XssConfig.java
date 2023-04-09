package com.upside.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class XssConfig   {    
    
	   private final ObjectMapper objectMapper;
	

   
    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
      ObjectMapper copy = objectMapper.copy();
      copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
      return new MappingJackson2HttpMessageConverter(copy);
    }
    
   
}