//package com.upside.api.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
//
//@Configuration
//public class XssConfig implements WebMvcConfigurer {
//
//	 @Bean
//	    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
//	        FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
//	        filterRegistrationBean.setFilter(new XssEscapeServletFilter());
//	        filterRegistrationBean.setOrder(1);
//	        filterRegistrationBean.addUrlPatterns("/*"); // 모든 요청에 필터 적용
//	        return filterRegistrationBean;
//	    }
//}