package com.hangeulbada.global.config;

import com.hangeulbada.domain.auth.component.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${custom.jwt.secretKey}")
    private String secretKey;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] WHITE_LIST = {
            "/users/**",
            "/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITE_LIST).permitAll())
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .anyRequest().permitAll())
//                .addFilterBefore(new JWTAuthFilter(secretKey, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        ;
        return http.build();
    }

}