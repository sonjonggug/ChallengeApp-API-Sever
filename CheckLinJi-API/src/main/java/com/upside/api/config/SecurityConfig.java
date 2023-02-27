package com.upside.api.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
@Configuration
@EnableWebSecurity // Spring Security의 웹 보안을 사용하도록 설정
@RequiredArgsConstructor // 어노테이션은 final로 선언된 필드를 자동으로 주입.
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // rest api이므로 csrf 보안 미사용
        .httpBasic().disable() // rest api이므로 기본설정 미사용        
        .formLogin().disable() // rest api 폼 로그인 인증 방식을 비활성화
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // jwt로 인증하므로 세션 미사용        
        http.authorizeHttpRequests() // HTTP 요청에 대한 인가 규칙을 설정
        		.requestMatchers("/api/members/sign/**").permitAll()  // login 없이 접근 허용 하는 URL
        		.requestMatchers("/api/members/login/**").permitAll()  // login 없이 접근 허용 하는 URL
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // admin 의 경우 ADMIN 권한이 있는 사용자만 접근이 가능
                .anyRequest().authenticated() // 그 외 모든 요청은 인증과정 필요               
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); //  JWT 인증 필터를 추가합니다.

        return http.build();
    }
              
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	/*
	 * .authorizeRequests() → .authorizeHttpRequests() .antMatchers() →
	 * .requestMatchers() .access("hasAnyRole('ROLE_A','ROLE_B')") →
	 * .hasAnyRole("A", "B")
	 */
}