package com.lsh.scheduler.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * security 의존성을 주입했기 때문에 모든 페이지에 대한 인증을 허용
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable) // Swagger를 사용했을 때 403에러를 반환을 막기 위해 사용
                .build();
    }
}
